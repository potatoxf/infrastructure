package potatoxf.infrastructure.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 弱引用，包裹引用，并对引用实现{@link #equals(Object)}，{@link #toString()}，{@link #hashCode()}
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class WeakEqualsReference<T> extends WeakReference<T> implements AboutReference<T> {
    public WeakEqualsReference(T referent) {
        super(referent);
    }

    public WeakEqualsReference(T referent, ReferenceQueue<? super T> queue) {
        super(referent, queue);
    }

    @Override
    public boolean equals(Object o) {
        return this.referenceEquals(o);
    }

    @Override
    public int hashCode() {
        return this.referenceHashCode();
    }

    @Override
    public String toString() {
        return this.referenceToString();
    }
}
