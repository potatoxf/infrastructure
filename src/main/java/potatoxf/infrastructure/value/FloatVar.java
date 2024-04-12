package potatoxf.infrastructure.value;

/**
 * {@link Float}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface FloatVar extends FloatVal, AnyNumberVar<Float> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static FloatVar of(Float value) {
        return new FloatVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link Float}类型
     *
     * @param value 输入值
     */
    @Override
    default Float parseValue(String value) throws Throwable {
        return Float.parseFloat(value);
    }

    /**
     * 从{@link Number}转为{@link Float}类型
     *
     * @param value 输入值
     */
    @Override
    default Float parseValue(Number value) throws Throwable {
        return value instanceof Float ? (Float) value : value.floatValue();
    }
}
