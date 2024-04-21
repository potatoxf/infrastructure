package potatoxf.infrastructure.ref;

/**
 * 可变基本引用
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
public class BasicVarReference<T> extends BasicValReference<T> implements AboutReference<T> {
    public BasicVarReference(T referent) {
        super(referent);
    }

    public void set(T referent) {
        this.referent = referent;
    }

    @Override
    public void clear() {
        this.referent = null;
    }
}
