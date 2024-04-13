package potatoxf.infrastructure.value.multi;

/**
 * {@code Type},{@code Key},{@code Value}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypeTripleVal<T, K, V> {
    static <T, K, V> AnyTypeTripleVal<T, K, V> of(T type, K key, V value) {
        return new AnyTypeTripleValImpl<>(type, key, value);
    }

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

    /**
     * 获取 {@link V}值
     *
     * @return 返回 {@link V}值
     */
    V getValue();
}
