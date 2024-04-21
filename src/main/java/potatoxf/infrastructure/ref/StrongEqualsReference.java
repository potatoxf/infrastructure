package potatoxf.infrastructure.ref;

/**
 * 强引用，包裹引用，并对引用实现{@link #equals(Object)}，{@link #toString()}，{@link #hashCode()}
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class StrongEqualsReference<T> extends BasicValReference<T> implements AboutReference<T> {
    public StrongEqualsReference(T referent) {
        super(referent);
    }
}
