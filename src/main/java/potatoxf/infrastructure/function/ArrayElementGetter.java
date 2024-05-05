package potatoxf.infrastructure.function;

/**
 * 数组元素获取器
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface ArrayElementGetter<A, E> {
    /**
     * 获取数组元素
     *
     * @param array 数组
     * @param index 索引
     * @return 返回数组元素
     */
    E apply(A array, int index);
}
