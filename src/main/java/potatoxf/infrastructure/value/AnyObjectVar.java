package potatoxf.infrastructure.value;

/**
 * {@link Object}变量
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyObjectVar extends AnyObjectVal, AnyTypeVar<Object> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static AnyObjectVar of(Object value) {
        return new AnyObjectVarImpl(value);
    }

    /**
     * 从{@link String}设置值
     *
     * @param value 输入值
     */
    @Override
    default void fromString(String value) {
        this.setValue(value);
    }
}
