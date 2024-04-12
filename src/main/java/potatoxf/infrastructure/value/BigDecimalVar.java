package potatoxf.infrastructure.value;

import java.math.BigDecimal;

/**
 * {@link BigDecimal}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface BigDecimalVar extends BigDecimalVal, AnyNumberVar<BigDecimal> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static BigDecimalVar of(BigDecimal value) {
        return new BigDecimalVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link BigDecimal}类型
     *
     * @param value 输入值
     */
    @Override
    default BigDecimal parseValue(String value) throws Throwable {
        return new BigDecimal(value);
    }

    /**
     * 从{@link Number}转为{@link BigDecimal}类型
     *
     * @param value 输入值
     */
    @Override
    default BigDecimal parseValue(Number value) throws Throwable {
        BigDecimal v = null;
        if (value != null) {
            v = BigDecimal.valueOf(value.doubleValue());
        }
        return v;
    }
}
