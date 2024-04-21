package potatoxf.infrastructure.ref;

import java.util.Objects;

/**
 * 用于寻找引用
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class LookupReference<T> extends BasicValReference<T> implements AboutReference<T> {
    private final int hashCode;

    public LookupReference(T referent) {
        this(referent, Objects.hashCode(referent));
    }

    public LookupReference(T referent, int hashCode) {
        super(referent);
        this.hashCode = hashCode;
    }

    @Override
    public int referenceHashCode() {
        return hashCode;
    }
}
