package potatoxf.infrastructure.value;

/**
 * {@link Object}值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyObjectVal extends AnyTypeVal<Object> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static AnyObjectVal of(Object value) {
        return new AnyObjectValImpl(value);
    }
}
