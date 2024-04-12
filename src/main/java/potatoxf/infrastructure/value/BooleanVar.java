package potatoxf.infrastructure.value;

/**
 * {@link Boolean}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface BooleanVar extends BooleanVal, AnyTypeVar<Boolean> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static BooleanVar of(Boolean value) {
        return new BooleanVarImpl(value);
    }

    /**
     * 从{@link String}设置值
     *
     * @param value 输入值
     */
    @Override
    default void fromString(String value) {
        Boolean v = null;
        if (value != null && !value.isEmpty()) {
            if ("t".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
                v = true;
            }
            if ("f".equalsIgnoreCase(value) || "0".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                v = false;
            }
        }
        this.setValue(v);
    }
}
