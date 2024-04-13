package potatoxf.infrastructure.value.multi;

/**
 * {@code Category},{@code Type},{@code Key}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypeTripleKeyVar<C, T, K> extends AnyTypeTripleKeyVal<C, T, K> {
    static <C, T, K> AnyTypeTripleKeyVar<C, T, K> of(C category, T type, K key) {
        return new AnyTypeTripleKeyVarImpl<>(category, type, key);
    }

    /**
     * 设置 {@link C}大类
     *
     * @param catalog {@link C}大类
     */
    void setCategory(C catalog);

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
     * 设置catalog,type,key
     *
     * @param input {@code TripleVal<C, T, K>}
     */
    default void set(AnyTypeTripleVal<C, T, K> input) {
        this.setCategory(input.getType());
        this.setType(input.getKey());
        this.setKey(input.getValue());
    }

    /**
     * 设置catalog,type,key
     *
     * @param input {@code PairKeyVal<K, V>}
     */
    default void set(AnyTypeTripleKeyVal<C, T, K> input) {
        this.setCategory(input.getCategory());
        this.setType(input.getType());
        this.setKey(input.getKey());
    }
}
