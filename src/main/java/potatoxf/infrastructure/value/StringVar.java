package potatoxf.infrastructure.value;

import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * {@link String}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface StringVar extends StringVal, AnyTypeVar<String> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static StringVar of(String value) {
        return new StringVarImpl(value);
    }

    /**
     * 设置{@link AnyTypeVal}的字符串值
     */
    default void fromValue(AnyTypeVal<?> value) {
        this.setValue(value == null ? null : value.toString());
    }

    /**
     * 从{@link String}设置值
     *
     * @param value     输入字符串
     * @param charset   编码
     * @param converter 转换器，将字符串通过一定逻辑转换成另一个字符串
     */
    default void fromString(String value, Charset charset, Function<byte[], String> converter) {
        if (value != null && converter != null) {
            value = converter.apply(charset == null ? value.getBytes() : value.getBytes(charset));
        }
        this.fromString(value);
    }

    /**
     * 从{@link String}设置值
     *
     * @param value     输入字符串
     * @param converter 转换器，将字符串通过一定逻辑转换成另一个字符串
     */
    default void fromString(String value, Function<String, String> converter) {
        if (value != null && converter != null) {
            value = converter.apply(value);
        }
        this.fromString(value);
    }

    /**
     * 从{@link String}设置值
     *
     * @param value 输入字符串
     */
    @Override
    default void fromString(String value) {
        this.setValue(value);
    }
}
