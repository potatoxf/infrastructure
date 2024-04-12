package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class AnyTypeVarImpl<V> extends AnyTypeValImpl<V> implements AnyTypeVar<V> {
    protected AnyTypeVarImpl(V value) {
        super(value);
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public void fromString(String value) {
        throw new UnsupportedOperationException();
    }
}
