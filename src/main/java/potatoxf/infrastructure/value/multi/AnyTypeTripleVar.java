package potatoxf.infrastructure.value.multi;

/**
 * {@code Type},{@code Key},{@code Value}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypeTripleVar<T, K, V> extends AnyTypeTripleVal<T, K, V> {
    static <T, K, V> AnyTypeTripleVar<T, K, V> of(T type, K key, V value) {
        return new AnyTypeTripleVarImpl<>(type, key, value);
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
     * @param key {@link K}类别
     */
    void setKey(K key);

    /**
     * 设置 {@link V}值
     *
     * @param value {@link V}类别
     */
    void setValue(V value);

    /**
     * 设置catalog,type,key
     *
     * @param input {@code TripleVal<T, K, V>}
     */
    default void set(AnyTypeTripleVal<T, K, V> input) {
        this.setType(input.getType());
        this.setKey(input.getKey());
        this.setValue(input.getValue());
    }

    /**
     * 设置catalog,type,key
     *
     * @param input {@code PairKeyVal<K, V>}
     */
    default void set(AnyTypeTripleKeyVal<T, K, V> input) {
        this.setType(input.getCategory());
        this.setKey(input.getType());
        this.setValue(input.getKey());
    }
}
