package potatoxf.infrastructure.value.multi;

import java.util.Objects;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypePairValImpl<K, V> implements AnyTypePairVal<K, V> {
    protected volatile K key;
    protected volatile V value;

    public AnyTypePairValImpl(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyTypePairValImpl<?, ?> that = (AnyTypePairValImpl<?, ?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
