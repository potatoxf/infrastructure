package potatoxf.infrastructure.value;

import java.math.BigInteger;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class BigIntegerValImpl extends AnyNumberValImpl<BigInteger> implements BigIntegerVal {
    protected BigIntegerValImpl(BigInteger value) {
        super(value);
    }
}
