package potatoxf.infrastructure.lot;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link Map}的值集合
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public class MapValueCollection<T> extends AbstractCollection<T> {
    private final Map<?, T> deletege;

    public MapValueCollection(Map<?, T> deletege) {
        this.deletege = deletege;
    }

    @Override
    public boolean isEmpty() {
        return deletege.isEmpty();
    }

    @Override
    public int size() {
        return deletege.size();
    }

    @Override
    public boolean contains(Object o) {
        //noinspection SuspiciousMethodCalls
        return deletege.containsValue(o);
    }

    @Override
    public void clear() {
        deletege.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorForMapEntryValue<>(deletege);
    }
}
