package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class BooleanVarImpl extends BooleanValImpl implements BooleanVar {
    protected BooleanVarImpl(Boolean value) {
        super(value);
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
