package potatoxf.infrastructure.value;

/**
 * {@link Number}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyNumberVal<T extends Number> extends AnyTypeVal<T> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static <T extends Number> AnyNumberVal<T> of(T value) {
        return new AnyNumberValImpl<>(value);
    }

    /**
     * 获取{@link Number}值
     *
     * @return {@link Number}值
     */
    default Number getNumber() {
        return getValue();
    }
}
