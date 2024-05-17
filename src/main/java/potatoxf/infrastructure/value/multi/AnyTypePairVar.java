package potatoxf.infrastructure.value.multi;

/**
 * {@code Key},{@code Value}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypePairVar<K, V> extends AnyTypePairVal<K, V> {
    static <K, V> AnyTypePairVar<K, V> of(K key, V value) {
        return new AnyTypePairVarImpl<>(key, value);
    }

    /**
     * 设置 {@link K}键
     *
     * @param key {@link K}键
     */
    void setKey(K key);

    /**
     * 设置 {@link V}值
     *
     * @param value {@link V}类别
     */
    V setValue(V value);

    /**
     * 设置key和value
     *
     * @param input {@code PairVal<K,V>}
     */
    default void set(AnyTypePairVal<K, V> input) {
        this.setKey(input.getKey());
        this.setValue(input.getValue());
    }

    /**
     * 设置key和value
     *
     * @param input {@code PairKeyVal<K, V>}
     */
    default void set(AnyTypePairKeyVal<K, V> input) {
        this.setKey(input.getType());
        this.setValue(input.getKey());
    }
}
