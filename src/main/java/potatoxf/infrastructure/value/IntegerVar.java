package potatoxf.infrastructure.value;

/**
 * {@link Integer}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface IntegerVar extends IntegerVal, AnyNumberVar<Integer> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static IntegerVar of(Integer value) {
        return new IntegerVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link Integer}类型
     *
     * @param value 输入值
     */
    @Override
    default Integer parseValue(String value) throws Throwable {
        return Integer.parseInt(value);
    }

    /**
     * 从{@link Number}转为{@link Integer}类型
     *
     * @param value 输入值
     */
    @Override
    default Integer parseValue(Number value) throws Throwable {
        return value instanceof Integer ? (Integer) value : value.intValue();
    }
}
