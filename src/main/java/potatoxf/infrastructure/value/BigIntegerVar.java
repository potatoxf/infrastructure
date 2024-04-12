package potatoxf.infrastructure.value;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * {@link BigInteger}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface BigIntegerVar extends BigIntegerVal, AnyNumberVar<BigInteger> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static BigIntegerVar of(BigInteger value) {
        return new BigIntegerVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link BigInteger}类型
     *
     * @param value 输入值
     */
    @Override
    default BigInteger parseValue(String value) throws Throwable {
        return new BigInteger(value);
    }

    /**
     * 从{@link Number}转为{@link BigInteger}类型
     *
     * @param value 输入值
     */
    @Override
    default BigInteger parseValue(Number value) throws Throwable {
        BigInteger v = null;
        if (value != null) {
            if (value instanceof BigInteger) v = (BigInteger) value;
            else if (value instanceof BigDecimal) v = ((BigDecimal) value).toBigIntegerExact();
            else if (value instanceof Double) v = BigDecimal.valueOf(value.doubleValue()).toBigIntegerExact();
            else if (value instanceof Float) v = BigDecimal.valueOf(value.floatValue()).toBigIntegerExact();
            else v = BigInteger.valueOf(value.longValue());
        }
        return v;
    }
}
