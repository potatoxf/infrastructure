package potatoxf.infrastructure.value;

import java.util.Iterator;
import java.util.List;

/**
 * {@link Value}的数组类型值
 * <p/>
 * Create Time:2024-05-07
 *
 * @author potatoxf
 */
public interface ArrayVal extends AnyTypeVal<Value>, Value, Iterable<Value> {

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.ARRAY;
    }

    /**
     * Returns number of elements in this array.
     */
    int size();

    /**
     * Returns the element at the specified position in this array.
     *
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    Value get(int index);

    /**
     * Returns the element at the specified position in this array.
     * This method returns an NilVar if the index is out of range.
     */
    Value getOrNilValue(int index);

    /**
     * Returns an iterator over elements.
     */
    Iterator<Value> iterator();

    /**
     * Returns the value as {@code List}.
     */
    List<Value> list();
}
