package potatoxf.infrastructure.value;

/**
 * {@link Short}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface ShortVar extends ShortVal, AnyNumberVar<Short> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static ShortVar of(Short value) {
        return new ShortVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link Short}类型
     *
     * @param value 输入值
     */
    @Override
    default Short parseValue(String value) throws Throwable {
        return Short.parseShort(value);
    }

    /**
     * 从{@link Number}转为{@link Short}类型
     *
     * @param value 输入值
     */
    @Override
    default Short parseValue(Number value) throws Throwable {
        return value instanceof Short ? (Short) value : value.shortValue();
    }
}
