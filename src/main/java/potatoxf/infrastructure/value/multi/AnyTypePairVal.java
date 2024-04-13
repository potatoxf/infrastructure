package potatoxf.infrastructure.value.multi;

/**
 * {@code Key},{@code Value}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypePairVal<K, V> {
    static <K, V> AnyTypePairVal<K, V> of(K key, V value) {
        return new AnyTypePairValImpl<>(key, value);
    }

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
