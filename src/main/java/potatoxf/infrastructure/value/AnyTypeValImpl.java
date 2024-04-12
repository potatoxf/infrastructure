package potatoxf.infrastructure.value;

import java.util.Objects;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class AnyTypeValImpl<V> implements AnyTypeVal<V> {
    volatile V value;

    protected AnyTypeValImpl(V value) {
        this.value = value;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value == null ? null : value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyTypeValImpl<?> i = (AnyTypeValImpl<?>) o;
        return Objects.equals(value, i.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
