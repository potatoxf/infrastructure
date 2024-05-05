package potatoxf.infrastructure.function;

/**
 * 数组元素设置器
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface ArrayElementSetter<A, E> {
    /**
     * 设置数组元素
     *
     * @param array   数组
     * @param index   索引
     * @param element 数组元素
     * @return 返回原来数组元素
     */
    E apply(A array, int index, E element);
}
