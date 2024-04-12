package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class ByteVarImpl extends ByteValImpl implements ByteVar {
    protected ByteVarImpl(Byte value) {
        super(value);
    }

    @Override
    public void setValue(Byte value) {
        this.value = value;
    }
}
