package potatoxf.infrastructure.value;

import potatoxf.infrastructure.Arg;

/**
 * {@link Float}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface FloatVal extends AnyNumberVal<Float> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static FloatVal of(Float value) {
        return new FloatValImpl(value);
    }

    /**
     * 获取{@link float}
     *
     * @return 返回{@link float}，如果为null则返回{@link Arg#DEFAULT_F}
     */
    default float primitiveValue() {
        Float value = getValue();
        if (value == null) return Arg.DEFAULT_F;
        return value;
    }
}
