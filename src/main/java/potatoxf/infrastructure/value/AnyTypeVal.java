package potatoxf.infrastructure.value;

/**
 * 任何类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyTypeVal<V> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static <V> AnyTypeVal<V> of(V value) {
        return new AnyTypeValImpl<>(value);
    }

    /**
     * 获取{@link V}值
     *
     * @return {@link V}值
     */
    V getValue();
}
