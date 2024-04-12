package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class FloatVarImpl extends FloatValImpl implements FloatVar {
    protected FloatVarImpl(Float value) {
        super(value);
    }

    @Override
    public void setValue(Float value) {
        this.value = value;
    }
}
