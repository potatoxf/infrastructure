package potatoxf.infrastructure.value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Representation of MessagePack's Map type.
 * <p>
 * MessagePack's Map type can represent sequence of key-value pairs.
 * <p/>
 * Create Time:2024-05-07
 *
 * @author potatoxf
 */
public interface MapVal extends AnyTypeVal<Value>, Value {
    /**
     * Returns number of key-value pairs in this array.
     */
    int size();

    Set<Value> keySet();

    Set<Map.Entry<Value, Value>> entrySet();

    Collection<Value> values();

    /**
     * Returns the value as {@code Map}.
     */
    Map<Value, Value> map();

    /**
     * Returns the key-value pairs as an array of {@code Value}.
     * <p>
     * Odd elements are keys. Next element of an odd element is a value corresponding to the key.
     * <p>
     * For example, if this value represents <code>{"k1": "v1", "k2": "v2"}</code>, this method returns ["k1", "v1", "k2", "v2"].
     */
    Value[] getKeyValueArray();
}
