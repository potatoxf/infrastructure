package potatoxf.infrastructure.ref;

/**
 * 强引用，包裹引用，并对引用实现{@link #equals(Object)}，{@link #toString()}，并使用指定{@link #hashCode}实现{@link #hashCode()}
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class StrongIdentityReference<T> extends StrongEqualsReference<T> {
    private final int hashCode;

    public StrongIdentityReference(T referent) {
        this(referent, System.identityHashCode(referent));
    }

    public StrongIdentityReference(T referent, int hashCode) {
        super(referent);
        this.hashCode = hashCode;
    }

    @Override
    public int referenceHashCode() {
        return hashCode;
    }
}
