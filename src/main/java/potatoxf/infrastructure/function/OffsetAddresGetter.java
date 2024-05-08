package potatoxf.infrastructure.function;

/**
 * 偏移地址获取器
 * <p/>
 * Create Time:2024-05-08
 *
 * @author potatoxf
 */
public interface OffsetAddresGetter<T, O, V> {

    /**
     * 获取值
     *
     * @param t      目标对象
     * @param o      操作对象
     * @param offset 偏移值
     * @return 值
     */
    V get(T t, O o, long offset);
}
