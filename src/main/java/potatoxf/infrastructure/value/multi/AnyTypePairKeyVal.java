package potatoxf.infrastructure.value.multi;

/**
 * {@code Type},{@code Key}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypePairKeyVal<T, K> {
    static <T, K> AnyTypePairKeyVal<T, K> of(T type, K key) {
        return new AnyTypePairKeyValImpl<>(type, key);
    }

    /**
     * 获取 {@link T}类别
     *
     * @return 返回 {@link T}类别
     */
    T getType();

    /**
     * 获取 {@link K}键
     *
     * @return 返回 {@link K}键
     */
    K getKey();
}
