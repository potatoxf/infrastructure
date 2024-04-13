package potatoxf.infrastructure.value.multi;

import java.util.Objects;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypePairKeyValImpl<T, K> implements AnyTypePairKeyVal<T, K> {
    protected volatile T type;
    protected volatile K key;

    public AnyTypePairKeyValImpl(T type, K key) {
        this.type = type;
        this.key = key;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyTypePairKeyValImpl<?, ?> that = (AnyTypePairKeyValImpl<?, ?>) o;
        return Objects.equals(type, that.type) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key);
    }

    @Override
    public String toString() {
        return type + "," + key;
    }
}
