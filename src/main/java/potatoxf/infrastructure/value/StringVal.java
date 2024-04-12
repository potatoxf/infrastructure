package potatoxf.infrastructure.value;

/**
 * {@link String}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface StringVal extends AnyTypeVal<String> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static StringVal of(String value) {
        return new StringValImpl(value);
    }
}
