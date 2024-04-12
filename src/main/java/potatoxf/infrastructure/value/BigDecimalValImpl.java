package potatoxf.infrastructure.value;

import java.math.BigDecimal;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class BigDecimalValImpl extends AnyNumberValImpl<BigDecimal> implements BigDecimalVal {
    protected BigDecimalValImpl(BigDecimal value) {
        super(value);
    }
}
