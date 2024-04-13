package potatoxf.infrastructure.value.multi;

import java.util.Objects;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypeTripleValImpl<T, K, V> implements AnyTypeTripleVal<T, K, V> {
    protected volatile T type;
    protected volatile K key;
    protected volatile V value;

    public AnyTypeTripleValImpl(T type, K key, V value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    @Override
    public T getType() {
        return type;
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
        AnyTypeTripleValImpl<?, ?, ?> that = (AnyTypeTripleValImpl<?, ?, ?>) o;
        return Objects.equals(type, that.type) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key);
    }

    @Override
    public String toString() {
        return type + "," + key + "=" + value;
    }
}
