package potatoxf.infrastructure.ref;

/**
 * 不可变基本引用
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class BasicValReference<T> implements AboutReference<T> {
    protected volatile T referent;

    public BasicValReference(T referent) {
        this.referent = referent;
    }

    @Override
    public T get() {
        return referent;
    }

    @Override
    public void clear() {
    }

    @Override
    public final boolean equals(Object o) {
        return this.referenceEquals(o);
    }

    @Override
    public final int hashCode() {
        return this.referenceHashCode();
    }

    @Override
    public final String toString() {
        return this.referenceToString();
    }
}
