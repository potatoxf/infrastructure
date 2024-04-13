package potatoxf.infrastructure.value.multi;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypeTripleVarImpl<T, K, V> extends AnyTypeTripleValImpl<T, K, V> implements AnyTypeTripleVar<T, K, V> {
    public AnyTypeTripleVarImpl(T type, K key, V value) {
        super(type, key, value);
    }

    @Override
    public void setType(T type) {
        this.type = type;
    }

    @Override
    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }
}
