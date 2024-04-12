package potatoxf.infrastructure.value;

import potatoxf.infrastructure.Arg;

/**
 * {@link Byte}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface ByteVal extends AnyNumberVal<Byte> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static ByteVal of(Byte value) {
        return new ByteValImpl(value);
    }

    /**
     * 获取{@link byte}
     *
     * @return 返回{@link byte}，如果为null则返回{@link Arg#DEFAULT_B}
     */
    default byte primitiveValue() {
        Byte value = getValue();
        if (value == null) return Arg.DEFAULT_B;
        return value;
    }
}
