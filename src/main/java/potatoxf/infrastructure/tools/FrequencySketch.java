package potatoxf.infrastructure.tools;


import lombok.Getter;

/**
 * 用于估计元素在时间窗口内的受欢迎程度的概率多集。这元件的最大频率限制为15和周期性老化过程将所有元素的受欢迎程度减半。
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
public final class FrequencySketch {
    /*
     * This class maintains a 4-bit CountMinSketch [1] with periodic aging to provide the popularity
     * history for the TinyLfu admission policy [2]. The time and space efficiency of the sketch
     * allows it to cheaply estimate the frequency of an entry in a stream of cache access events.
     *
     * The counter matrix is represented as a single dimensional array holding 16 counters per slot. A
     * fixed depth of four balances the accuracy and cost, resulting in a width of four times the
     * length of the array. To retain an accurate estimation the array's length equals the maximum
     * number of entries in the cache, increased to the closest power-of-two to exploit more efficient
     * bit masking. This configuration results in a confidence of 93.75% and error bound of e / width.
     *
     * The frequency of all entries is aged periodically using a sampling window based on the maximum
     * number of entries in the cache. This is referred to as the reset operation by TinyLfu and keeps
     * the sketch fresh by dividing all counters by two and subtracting based on the number of odd
     * counters found. The O(n) cost of aging is amortized, ideal for hardware prefetching, and uses
     * inexpensive bit manipulations per array location.
     *
     * [1] An Improved Data Stream Summary: The Count-Min Sketch and its Applications
     * http://dimacs.rutgers.edu/~graham/pubs/papers/cm-full.pdf
     * [2] TinyLFU: A Highly Efficient Cache Admission Policy
     * https://dl.acm.org/citation.cfm?id=3149371
     */

    // A mixture of seeds from FNV-1a, CityHash, and Murmur3
    private static final long[] SPREAD = {0xc3a5c85c97cb3127L, 0xb492b66fbe98f273L, 0x9ae16a3b2f90404fL, 0xcbf29ce484222325L};
    @Getter
    private volatile long sampleSize;
    private volatile int tableMask;
    private volatile long[] table;
    private volatile long size;

    public FrequencySketch() {

    }

    /**
     * 初始化table，若maximumSize=0，table长度为1（无界缓存，所有数据都被记录下来，也就无需记录每个数据的频次了）
     * 否则table长度为大于等于maximumSize的最小的2的整数倍。sampleSize=10倍数组长度。
     * 数组长度最大为Integer.MAX_VALUE的二分之一，也就是2147483647/2=1073741823，大概10.7亿
     *
     * @param maximumSize 缓存的最大大小
     */
    public void ensureCapacity(long maximumSize) {
        int maximum = (int) Math.min(Math.max(maximumSize, 1), Integer.MAX_VALUE >>> 1);
        if (table != null && table.length >= maximum) return;
        table = new long[1 << -Integer.numberOfLeadingZeros(maximum - 1)];
        tableMask = Math.max(0, table.length - 1);
        sampleSize = 10L * tableMask;
        size = 0;
    }

    /**
     * 如果草图尚未初始化，则返回，要求 {@link #ensureCapacity} 为在开始跟踪频率之前调用。
     */
    public boolean isNotInitialized() {
        return table == null;
    }

    /**
     * 获取元素的频次，由于每个元素都有4个hash算法，在4个位置记录了4个频次，取其中最小的频次作为该元素的频次
     *
     * @param element 元素值
     * @return 元素的估计出现次数，可能为零，但绝不是负数。
     */
    public int frequency(Object element) {
        if (isNotInitialized()) return 0;
        int hash = spread(element.hashCode());
        //start为counter下标，这个算法start只可能为0 4 8 12的其中一种
        int start = (hash & 3) << 2;
        int frequency = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            //index为每个slot下标
            int index = indexOfSlot(hash, i);
            //table[index]为当前slot的long整数。
            //假设start为4，二进制为0100，table[index]的二进制为0100 1010... 1101 0011 1000 0011 1001
            //(start + i) << 2 左移两位0001 0000，也就是十进制16，其实就是从右数，下标为4的counter的二进制的右边第一位，这个counter就是1101
            //(table[index] >>> 16也就是0000 0000 0000 0000 0100 1010... 1101
            //oxfL也就是15，二进制为0000 0000...0000 1111，跟"0000 0000 0000 0000 0100 1010... 1101"相与，就是只保留后4位，1101，也就是这个counter的值=该元素在该位置的频次
            int count = (int) ((table[index] >>> ((start + i) << 2)) & 0xfL);
            frequency = Math.min(frequency, count);
        }
        return frequency;
    }

    /**
     * 增加元素的频次，获取4个slot位置，获取counter位置，并为每个counter加1。
     * <p>
     * 假设slot的counter下标=c。slot1的counter下标等于c+1，slot2的counter下标等于c+2，slot3的counter下标等于c+3。
     * 这么做是因为计算counter的算法得到的只能为0,4,8,12这4种情况，为了使用所有16个counter。
     *
     * @param element 元素值
     */
    public void increment(Object element) {
        if (isNotInitialized()) return;

        //hash后再打乱一次，使hashcode更加均匀
        int hash = spread(element.hashCode());

        //使用hash值二进制后两位计算counter下标
        int start = (hash & 3) << 2;

        //用不同seed获取4个slot下标
        // Loop unrolling improves throughput by 5m ops/s
        int index0 = indexOfSlot(hash, 0);
        int index1 = indexOfSlot(hash, 1);
        int index2 = indexOfSlot(hash, 2);
        int index3 = indexOfSlot(hash, 3);

        //尝试在每个counter上加1，最多15
        boolean added = incrementAtCounter(index0, start);
        added |= incrementAtCounter(index1, start + 1);
        added |= incrementAtCounter(index2, start + 2);
        added |= incrementAtCounter(index3, start + 3);

        if (added && (++size == sampleSize)) {
            //Reduces every counter by half of its original value.
            int count = 0;
            for (int i = 0; i < table.length; i++) {
                count += Long.bitCount(table[i] & 0x1111111111111111L);
                //16个counter中频次为奇数的个数
                //table[i] >>> 1，整体右移1位，其中每4个bit也右移1位，相当于除2。但每个counter的高位是上一个bit的低位，可能为1
                //& RESET_MASK，抹去新counter的最高位，保留低三位。最终实现每个counter除2
                //        1100 1001 0001 0010
                // 右移1位 0110 0100 1000 1001
                //  相与后 0110 0100 0000 0001
                table[i] = (table[i] >>> 1) & Long.MAX_VALUE;
            }
            size = (size - (count >>> 2)) >>> 1;
        }
    }

    /**
     * 64bit分为16个counter，在counter所在的4位bit加1，最大值15。
     *
     * @param slotIndex    slot索引
     * @param counterIndex counter索引
     * @return 如果成功增加则返回true，否则false
     */
    private boolean incrementAtCounter(int slotIndex, int counterIndex) {
        //假如：slotIndex=3，counterIndex=3。
        int offset = counterIndex << 2;
        //long为64个bit,4个bit为一个counter，offfset为counter在64bit的偏移位置则offset=3<<2=12。

        long mask = (0xFL << offset);
        //mask为掩码，这个偏移offset的counter为4个bit为1111值。
        //则mask=0xFL<<12=00000000 00001111 00000000 00000000 00000000 00000000 00000000 00000000

        if ((table[slotIndex] & mask) == mask) return false;
        //(table[slotIndex] & mask) != mask，表示那4bit不全为1，也就是不等于15。

        long increment = 1L << offset;
        //increment为偏移offset的counter需要加1的值。
        //则increment=0x1L<<12=00000000 00000001 00000000 00000000 00000000 00000000 00000000 00000000

        table[slotIndex] += increment;
        //假如table[slotIndex]的二进制为：xxxxxxxx xxxx1101 xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        //那么counter的4bit为1101，十进制为13。
        //  xxxxxxxx xxxx1101 xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        //+ 00000000 00000001 00000000 00000000 00000000 00000000 00000000 00000000
        //= xxxxxxxx xxxx1110 xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx xxxxxxxx
        //相加后1101变成1110，十进制=14
        return true;
    }

    /**
     * 获取Slot索引。
     *
     * @param itemHashCode 项目元素的哈希值
     * @param spreadIndex  分散hashCode值的索引,范围在0-3
     * @return 返回Slot索引
     */
    private int indexOfSlot(int itemHashCode, int spreadIndex) {
        long hash = (itemHashCode + SPREAD[spreadIndex]) * SPREAD[spreadIndex];
        hash += (hash >>> 32);
        return ((int) hash) & tableMask;
    }

    /**
     * 将补充哈希函数应用于给定的哈希代码，以抵御质量低劣的哈希函数。
     */
    private int spread(int x) {
        x = ((x >>> 16) ^ x) * 0x45d9f3b;
        x = ((x >>> 16) ^ x) * 0x45d9f3b;
        return (x >>> 16) ^ x;
    }
}
