package potatoxf.infrastructure.value;

import potatoxf.api.support.Arg;

/**
 * {@link Character}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface CharacterVal extends AnyTypeVal<Character>, Value {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static CharacterVal of(Character value) {
        return new CharacterValImpl(value);
    }

    /**
     * 获取{@link char}
     *
     * @return 返回{@link char}，如果为null则返回{@link Arg#DEFAULT_C}
     */
    default char primitiveValue() {
        Character value = getValue();
        if (value == null) return Arg.DEFAULT_C;
        return value;
    }

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.STRING;
    }
}
