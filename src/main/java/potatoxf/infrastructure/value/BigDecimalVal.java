package potatoxf.infrastructure.value;

import java.math.BigDecimal;

/**
 * {@link BigDecimal}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface BigDecimalVal extends AnyNumberVal<BigDecimal> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static BigDecimalVal of(BigDecimal value) {
        return new BigDecimalValImpl(value);
    }
}
