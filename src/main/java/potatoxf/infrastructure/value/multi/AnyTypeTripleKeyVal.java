package potatoxf.infrastructure.value.multi;

/**
 * {@code Category},{@code Type},{@code Key}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypeTripleKeyVal<C, T, K> {
    static <C, T, K> AnyTypeTripleKeyVal<C, T, K> of(C category, T type, K key) {
        return new AnyTypeTripleKeyValImpl<>(category, type, key);
    }

    /**
     * 获取 {@link C}大类
     *
     * @return 返回 {@link C}大类
     */
    C getCategory();

    /**
     * 获取 {@link T}类别
     *
     * @return 返回 {@link T}类别
     */
    T getType();

    /**
     * 获取 {@link K}键
     *
     * @return 返回 {@link K}键
     */
    K getKey();
}
