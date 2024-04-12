package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class LongVarImpl extends LongValImpl implements LongVar {
    protected LongVarImpl(Long value) {
        super(value);
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
    }
}
