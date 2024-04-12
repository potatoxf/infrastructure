package potatoxf.infrastructure.value;

/**
 * 任何类型变量
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface AnyTypeVar<V> extends AnyTypeVal<V> {
    /**
     * 默认构造该接口
     *
     * @param value 输入值
     * @return 返回该接口
     */
    static <V> AnyTypeVar<V> of(V value) {
        return new AnyTypeVarImpl<>(value);
    }

    /**
     * 设置{@link V}值
     */
    void setValue(V value);

    /**
     * 从{@link String}设置值
     *
     * @param value 输入值
     */
    void fromString(String value);
}
