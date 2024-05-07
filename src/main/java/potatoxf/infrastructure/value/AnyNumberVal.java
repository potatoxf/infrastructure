package potatoxf.infrastructure.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

/**
 * {@link Number}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyNumberVal<T extends Number> extends AnyTypeVal<T>, Value {
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

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        Number number = getNumber();
        if (number != null) {
            if (number instanceof Integer) return ValueType.INTEGER;
            if (number instanceof Double) return ValueType.DOUBLE;
            if (number instanceof Long) return ValueType.LONG;
            if (number instanceof Float) return ValueType.FLOAT;
            if (number instanceof Byte) return ValueType.BYTE;
            if (number instanceof Short) return ValueType.SHORT;
            if (number instanceof BigInteger) return ValueType.BIG_INTEGER;
            if (number instanceof BigDecimal) return ValueType.BIG_DECIMAL;
        } else {
            return ValueType.NIL;
        }
        return ValueType.DOUBLE;
    }

    /**
     * 将此值表示为{@link byte}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default byte toByte() {
        return getNumber().byteValue();
    }

    /**
     * 将此值表示为{@link short}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default short toShort() {
        return getNumber().shortValue();
    }

    /**
     * 将此值表示为{@link int}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default int toInt() {
        return getNumber().intValue();
    }

    /**
     * 将此值表示为{@link long}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default long toLong() {
        return getNumber().longValue();
    }

    /**
     * 将此值表示为{@link float}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default float toFloat() {
        return getNumber().floatValue();
    }

    /**
     * 将此值表示为{@link double}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default double toDouble() {
        return getNumber().doubleValue();
    }

    /**
     * 将此值表示为{@link BigInteger}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default BigInteger toBigInteger() {
        return BigInteger.valueOf(getValue().longValue());
    }

    /**
     * 将此值表示为{@link BigDecimal}值，这可能涉及对原始值的舍入或截断。
     *
     * @return 返回值
     */
    default BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(getValue().doubleValue());
    }

    /**
     * 判断数字是否是8位
     *
     * @return 如果是返回true，否则返回false
     */
    default boolean is8bit() {
        return false;
    }

    /**
     * 判断数字是否是16位
     *
     * @return 如果是返回true，否则返回false
     */
    default boolean is16bit() {
        return false;
    }

    /**
     * 判断数字是否是32位
     *
     * @return 如果是返回true，否则返回false
     */
    default boolean is32bit() {
        return false;
    }

    /**
     * 判断数字是否是64位
     *
     * @return 如果是返回true，否则返回false
     */
    default boolean is64bit() {
        return false;
    }

    /**
     * 判断数字是否不确定位数
     *
     * @return 如果是返回true，否则返回false
     */
    default boolean isExbit() {
        return false;
    }

    /**
     * Returns the most succinct MessageFormat type to represent this integer value.
     *
     * @return the smallest integer type of MessageFormat that is big enough to store the value.
     */
    default MessageFormat mostSuccinctMessageFormat() {
        return null;
    }
}
