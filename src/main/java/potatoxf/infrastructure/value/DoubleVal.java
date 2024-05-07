package potatoxf.infrastructure.value;

import potatoxf.infrastructure.Arg;

/**
 * {@link Double}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface DoubleVal extends AnyNumberVal<Double> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static DoubleVal of(Double value) {
        return new DoubleValImpl(value);
    }

    /**
     * 获取{@link double}
     *
     * @return 返回{@link double}，如果为null则返回{@link Arg#DEFAULT_D}
     */
    default double primitiveValue() {
        Double value = getValue();
        if (value == null) return Arg.DEFAULT_D;
        return value;
    }

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.DOUBLE;
    }

    /**
     * 判断数字是否是64位
     *
     * @return 如果是返回true，否则返回false
     */
    @Override
    default boolean is64bit() {
        return true;
    }
}
