package potatoxf.infrastructure.value;

/**
 * {@link Character}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface CharacterVar extends CharacterVal, AnyTypeVar<Character> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static CharacterVar of(Character value) {
        return new CharacterVarImpl(value);
    }

    /**
     * 从{@link String}设置值
     *
     * @param value 输入值
     */
    @Override
    default void fromString(String value) {
        Character v = null;
        if (value != null && !value.isEmpty()) {
            int length = value.length();
            if (length == 1) {
                v = value.charAt(0);
            } else {
                throw new IllegalArgumentException("The input string '" + value + "' length is not 1");
            }
        }
        this.setValue(v);
    }
}
