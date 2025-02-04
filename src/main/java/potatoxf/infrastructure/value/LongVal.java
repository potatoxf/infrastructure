package potatoxf.infrastructure.value;

import potatoxf.api.support.Arg;

/**
 * {@link Long}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface LongVal extends AnyNumberVal<Long> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static LongVal of(Long value) {
        return new LongValImpl(value);
    }

    /**
     * 获取{@link long}
     *
     * @return 返回{@link long}，如果为null则返回{@link Arg#DEFAULT_J}
     */
    default long primitiveValue() {
        Long value = getValue();
        if (value == null) return Arg.DEFAULT_J;
        return value;
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
