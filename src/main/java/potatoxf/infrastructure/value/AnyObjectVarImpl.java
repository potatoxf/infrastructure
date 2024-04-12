package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class AnyObjectVarImpl extends AnyObjectValImpl implements AnyObjectVar {
    protected AnyObjectVarImpl(Object value) {
        super(value);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }
}
