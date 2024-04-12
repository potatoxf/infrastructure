package potatoxf.infrastructure.value;

import java.math.BigDecimal;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class BigDecimalVarImpl extends BigDecimalValImpl implements BigDecimalVar {
    protected BigDecimalVarImpl(BigDecimal value) {
        super(value);
    }

    @Override
    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
