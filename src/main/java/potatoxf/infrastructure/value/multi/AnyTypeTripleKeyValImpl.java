package potatoxf.infrastructure.value.multi;

import java.util.Objects;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypeTripleKeyValImpl<C, T, K> implements AnyTypeTripleKeyVal<C, T, K> {
    protected volatile C category;
    protected volatile T type;
    protected volatile K key;

    public AnyTypeTripleKeyValImpl(C category, T type, K key) {
        this.category = category;
        this.type = type;
        this.key = key;
    }

    @Override
    public C getCategory() {
        return category;
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
        AnyTypeTripleKeyValImpl<?, ?, ?> that = (AnyTypeTripleKeyValImpl<?, ?, ?>) o;
        return Objects.equals(category, that.category) && Objects.equals(type, that.type) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, type, key);
    }

    @Override
    public String toString() {
        return category + "," + type + "," + key;
    }
}
