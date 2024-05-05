package potatoxf.infrastructure.function;

/**
 * 数组重置大小调整器
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface ArrayResizor<A> {
    /**
     * 数组重置大小
     *
     * @param array    数组
     * @param capacity 数组容量
     * @param size     当前已使用大小
     * @return 返回处理后的数组
     */
    A apply(A array, int capacity, int size);
}
