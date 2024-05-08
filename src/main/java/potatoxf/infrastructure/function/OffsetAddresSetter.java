package potatoxf.infrastructure.function;

/**
 * 偏移地址设置器
 * <p/>
 * Create Time:2024-05-08
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface OffsetAddresSetter<T, O, V> {

    /**
     * 设置值
     *
     * @param t      目标对象
     * @param o      操作对象
     * @param offset 偏移值
     * @param v      值
     */
    void set(T t, O o, long offset, V v);
}
