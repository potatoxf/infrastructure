package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class StringVarImpl extends StringValImpl implements StringVar {
    protected StringVarImpl(String value) {
        super(value);
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
