package potatoxf.infrastructure.value;

/**
 * {@link Long}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface LongVar extends LongVal, AnyNumberVar<Long> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static LongVar of(Long value) {
        return new LongVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link Long}类型
     *
     * @param value 输入值
     */
    @Override
    default Long parseValue(String value) throws Throwable {
        return Long.parseLong(value);
    }

    /**
     * 从{@link Number}转为{@link Long}类型
     *
     * @param value 输入值
     */
    @Override
    default Long parseValue(Number value) throws Throwable {
        return value instanceof Long ? (Long) value : value.longValue();
    }
}
