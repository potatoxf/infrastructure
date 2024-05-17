package potatoxf.infrastructure.value.multi;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypePairVarImpl<K, V> extends AnyTypePairValImpl<K, V> implements AnyTypePairVar<K, V> {
    public AnyTypePairVarImpl(K key, V value) {
        super(key, value);
    }

    @Override
    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
