package potatoxf.infrastructure.utils;

/**
 * 数学工具类
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public class KitForMath {
    public static int log2p(int x) {
        int r = 0;
        while ((x >>= 1) != 0) {
            r++;
        }
        return r;
    }

    public static int minDiff(int... offs) {
        int min = Integer.MAX_VALUE;
        for (int o1 : offs) {
            for (int o2 : offs) {
                if (o1 != o2) {
                    min = Math.min(min, Math.abs(o1 - o2));
                }
            }
        }
        return min;
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int pow2(int power) {
        int p = 1;
        for (int i = 0; i < power; i++) {
            p *= 2;
        }
        return p;
    }

    /**
     * 将参数与给定的对齐方式对齐。 对齐方式应为 2 的幂。
     *
     * @param v value to align
     * @param a alignment, should be power of two
     * @return aligned value
     */
    public static int align(int v, int a) {
        return (v + a - 1) & -a;
    }

    /**
     * Aligns the argument to the given alignment.
     * Alignment should be a power of two.
     *
     * @param v value to align
     * @param a alignment, should be power of two
     * @return aligned value
     */
    public static long align(long v, int a) {
        return (v + a - 1) & -a;
    }

    /**
     * Returns the smallest power of two greater than or equal to {@code x}.
     */
    public static int ceilingPowerOfTwo(int x) {
        // From Hacker's Delight, Chapter 3, Harry S. Warren Jr.
        return 1 << -Integer.numberOfLeadingZeros(x - 1);
    }

    /**
     * Returns the smallest power of two greater than or equal to {@code x}.
     */
    public static long ceilingPowerOfTwo(long x) {
        // From Hacker's Delight, Chapter 3, Harry S. Warren Jr.
        return 1L << -Long.numberOfLeadingZeros(x - 1);
    }
}
