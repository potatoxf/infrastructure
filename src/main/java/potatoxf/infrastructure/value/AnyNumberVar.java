package potatoxf.infrastructure.value;

import potatoxf.infrastructure.Log;

/**
 * {@link Number}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyNumberVar<V extends Number> extends AnyNumberVal<V>, AnyTypeVar<V> {
    /**
     * 从{@link String}转为{@link V}类型
     *
     * @param value 输入值
     */
    V parseValue(String value) throws Throwable;

    /**
     * 从{@link Number}转为{@link V}类型
     *
     * @param value 输入值
     */
    V parseValue(Number value) throws Throwable;

    /**
     * 设置{@link AnyNumberVal<Number>}值
     */
    default void fromNumber(AnyNumberVal<? extends Number> value) {
        V v = null;
        if (value != null) {
            Number number = value.getNumber();
            if (number != null) {
                this.fromNumber(number);
            }
        }
        setValue(v);
    }

    /**
     * 设置{@link Number}值
     */
    default void fromNumber(Number value) {
        V v = null;
        if (value != null) {
            try {
                v = this.parseValue(value);
            } catch (Throwable e) {
                if (Log.isEnabledError()) {
                    Log.error("Error to parser number with '%s'", value, e);
                }
            }
        }
        setValue(v);
    }

    /**
     * 从{@link String}设置值
     *
     * @param value 输入值
     */
    @Override
    default void fromString(String value) {
        V v = null;
        if (value != null) {
            try {
                v = this.parseValue(value);
            } catch (Throwable e) {
                if (Log.isEnabledError()) {
                    Log.error("Error to parser number with '%s'", value, e);
                }
            }
        }
        setValue(v);
    }
}
