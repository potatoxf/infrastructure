package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class IntegerVarImpl extends IntegerValImpl implements IntegerVar {
    protected IntegerVarImpl(Integer value) {
        super(value);
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
}
