package potatoxf.infrastructure.value;

import java.math.BigInteger;

/**
 * {@link BigInteger}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface BigIntegerVal extends AnyNumberVal<BigInteger> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static BigIntegerVal of(BigInteger value) {
        return new BigIntegerValImpl(value);
    }

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.BIG_INTEGER;
    }

    /**
     * 判断数字是否不确定位数
     *
     * @return 如果是返回true，否则返回false
     */
    @Override
    default boolean isExbit() {
        return true;
    }
}
