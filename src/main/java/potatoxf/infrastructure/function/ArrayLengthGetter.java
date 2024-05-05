package potatoxf.infrastructure.function;

/**
 * 数组长度获取器
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface ArrayLengthGetter<A> {
    /**
     * 获取数组长度
     * @param array 数组
     * @return 返回数组长度
     */
    int apply(A array);
}
