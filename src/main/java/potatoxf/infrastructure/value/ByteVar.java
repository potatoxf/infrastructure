package potatoxf.infrastructure.value;

/**
 * {@link Byte}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface ByteVar extends ByteVal, AnyNumberVar<Byte> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static ByteVar of(Byte value) {
        return new ByteVarImpl(value);
    }

    /**
     * 从{@link String}转为{@link Byte}类型
     *
     * @param value 输入值
     */
    @Override
    default Byte parseValue(String value) throws Throwable {
        return Byte.parseByte(value);
    }

    /**
     * 从{@link Number}转为{@link Byte}类型
     *
     * @param value 输入值
     */
    @Override
    default Byte parseValue(Number value) throws Throwable {
        return value instanceof Byte ? (Byte) value : value.byteValue();
    }
}
