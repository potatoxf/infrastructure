package potatoxf.infrastructure.value;

import java.nio.ByteBuffer;

/**
 * <p/>
 * Create Time:2024-05-07
 *
 * @author potatoxf
 */
public interface Value extends ValueTypeConverter, ValueTypeJudgment {

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    ValueType valueType();

    /**
     * Returns the value as {@code byte[]}.
     * <p>
     * This method copies the byte array.
     */
    default byte[] asByteArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ByteBuffer}.
     * <p>
     * Returned ByteBuffer is read-only. See also {@link ByteBuffer#asReadOnlyBuffer()}.
     * This method doesn't copy the byte array as much as possible.
     */
    default ByteBuffer asByteBuffer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code String}.
     * <p>
     * This method throws an exception if the value includes invalid UTF-8 byte sequence.
     */
    default String asString() {
        return toString();
    }

}
