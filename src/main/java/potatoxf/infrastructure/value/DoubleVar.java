package potatoxf.infrastructure.value;

/**
 * {@link Double}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface DoubleVar extends DoubleVal, AnyNumberVar<Double> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static DoubleVar of(Double value) {
        return new DoubleVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link Double}类型
     *
     * @param value 输入值
     */
    @Override
    default Double parseValue(String value) throws Throwable {
        return Double.parseDouble(value);
    }

    /**
     * 从{@link Number}转为{@link Double}类型
     *
     * @param value 输入值
     */
    @Override
    default Double parseValue(Number value) throws Throwable {
        return value instanceof Double ? (Double) value : value.doubleValue();
    }
}
