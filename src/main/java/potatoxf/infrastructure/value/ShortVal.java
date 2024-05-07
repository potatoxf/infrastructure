package potatoxf.infrastructure.value;

import potatoxf.infrastructure.Arg;

/**
 * {@link Short}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface ShortVal extends AnyNumberVal<Short> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static ShortVal of(Short value) {
        return new ShortValImpl(value);
    }

    /**
     * 获取{@link short}
     *
     * @return 返回{@link short}，如果为null则返回{@link Arg#DEFAULT_S}
     */
    default short primitiveValue() {
        Short value = getValue();
        if (value == null) return Arg.DEFAULT_S;
        return value;
    }

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.SHORT;
    }

    /**
     * 判断数字是否是16位
     *
     * @return 如果是返回true，否则返回false
     */
    @Override
    default boolean is16bit() {
        return true;
    }
}
