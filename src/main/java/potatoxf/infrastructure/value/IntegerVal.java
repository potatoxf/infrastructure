package potatoxf.infrastructure.value;

import potatoxf.api.support.Arg;

/**
 * {@link Integer}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface IntegerVal extends AnyNumberVal<Integer> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static IntegerVal of(Integer value) {
        return new IntegerValImpl(value);
    }

    /**
     * 获取{@link int}
     *
     * @return 返回{@link int}，如果为null则返回{@link Arg#DEFAULT_I}
     */
    default int primitiveValue() {
        Integer value = getValue();
        if (value == null) return Arg.DEFAULT_I;
        return value;
    }

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.INTEGER;
    }

    /**
     * 判断数字是否是32位
     *
     * @return 如果是返回true，否则返回false
     */
    @Override
    default boolean is32bit() {
        return true;
    }
}
