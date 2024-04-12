package potatoxf.infrastructure.value;

import java.math.BigInteger;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class BigIntegerVarImpl extends BigIntegerValImpl implements BigIntegerVar {
    protected BigIntegerVarImpl(BigInteger value) {
        super(value);
    }

    @Override
    public void setValue(BigInteger value) {
        this.value = value;
    }
}
