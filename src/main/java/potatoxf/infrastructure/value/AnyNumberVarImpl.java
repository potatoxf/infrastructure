package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public abstract class AnyNumberVarImpl<T extends Number> extends AnyNumberValImpl<T> implements AnyNumberVar<T> {
    protected AnyNumberVarImpl(T value) {
        super(value);
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
