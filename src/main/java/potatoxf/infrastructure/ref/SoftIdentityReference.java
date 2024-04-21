package potatoxf.infrastructure.ref;

import java.lang.ref.ReferenceQueue;

/**
 * 软引用，包裹引用，并对引用实现{@link #equals(Object)}，{@link #toString()}，并使用指定{@link #hashCode}实现{@link #hashCode()}
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class SoftIdentityReference<T> extends SoftEqualsReference<T> implements AboutReference<T> {
    private final int hashCode;

    public SoftIdentityReference(T referent) {
        this(referent, null, System.identityHashCode(referent));
    }

    public SoftIdentityReference(T referent, int hashCode) {
        this(referent, null, hashCode);
    }

    public SoftIdentityReference(T referent, ReferenceQueue<? super T> queue) {
        this(referent, queue, System.identityHashCode(referent));
    }

    public SoftIdentityReference(T referent, ReferenceQueue<? super T> queue, int hashCode) {
        super(referent, queue);
        this.hashCode = hashCode;
    }

    @Override
    public int referenceHashCode() {
        return hashCode;
    }
}
