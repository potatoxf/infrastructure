package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class ShortVarImpl extends ShortValImpl implements ShortVar {
    protected ShortVarImpl(Short value) {
        super(value);
    }

    @Override
    public void setValue(Short value) {
        this.value = value;
    }
}
