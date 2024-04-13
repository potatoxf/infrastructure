package potatoxf.infrastructure.value.multi;

/**
 * {@code Type},{@code Key}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypePairKeyVar<T, K> extends AnyTypePairKeyVal<T, K> {
    static <T, K> AnyTypePairKeyVar<T, K> of(T type, K key) {
        return new AnyTypePairKeyVarImpl<>(type, key);
    }

    /**
     * 设置 {@link T}类别
     *
     * @param type {@link T}类别
     */
    void setType(T type);

    /**
     * 设置 {@link K}键
     *
     * @param key {@link K}键
     */
    void setKey(K key);

    /**
     * 设置type和key
     *
     * @param input {@code PairVal<T, K>}
     */
    default void set(AnyTypePairVal<T, K> input) {
        this.setType(input.getKey());
        this.setKey(input.getValue());
    }

    /**
     * 设置type和key
     *
     * @param input {@code PairVal<T, K>}
     */
    default void set(AnyTypePairKeyVal<T, K> input) {
        this.setType(input.getType());
        this.setKey(input.getKey());
    }
}
