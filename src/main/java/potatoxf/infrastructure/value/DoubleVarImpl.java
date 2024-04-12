package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class DoubleVarImpl extends DoubleValImpl implements DoubleVar {
    protected DoubleVarImpl(Double value) {
        super(value);
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }
}
