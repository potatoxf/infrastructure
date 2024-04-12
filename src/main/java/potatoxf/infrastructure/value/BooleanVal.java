package potatoxf.infrastructure.value;

import potatoxf.infrastructure.Arg;

/**
 * {@link Boolean}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface BooleanVal extends AnyTypeVal<Boolean> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static BooleanVal of(Boolean value) {
        return new BooleanValImpl(value);
    }

    /**
     * 获取{@link boolean}
     *
     * @return 返回{@link boolean}，如果为null则返回{@link Arg#DEFAULT_Z}
     */
    default boolean primitiveValue() {
        Boolean value = getValue();
        if (value == null) return Arg.DEFAULT_Z;
        return value;
    }
}
