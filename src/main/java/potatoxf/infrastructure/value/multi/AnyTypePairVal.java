package potatoxf.infrastructure.value.multi;

import java.util.Map;

/**
 * {@code Key},{@code Value}实体值
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
public interface AnyTypePairVal<K, V> extends Map.Entry<K, V> {
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

    /**
     * Replaces the value corresponding to this entry with the specified
     * value (optional operation).  (Writes through to the map.)  The
     * behavior of this call is undefined if the mapping has already been
     * removed from the map (by the iterator's <tt>remove</tt> operation).
     *
     * @param value new value to be stored in this entry
     * @return old value corresponding to the entry
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *                                       is not supported by the backing map
     * @throws ClassCastException            if the class of the specified value
     *                                       prevents it from being stored in the backing map
     * @throws NullPointerException          if the backing map does not permit
     *                                       null values, and the specified value is null
     * @throws IllegalArgumentException      if some property of this value
     *                                       prevents it from being stored in the backing map
     * @throws IllegalStateException         implementations may, but are not
     *                                       required to, throw this exception if the entry has been
     *                                       removed from the backing map.
     */
    @Override
    default V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}
