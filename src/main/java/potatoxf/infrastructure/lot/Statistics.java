package potatoxf.infrastructure.lot;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 统计
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public final class Statistics<E> extends AbstractCollection<E> implements Collection<E> {
    private final HashMap<E, Long> map = new HashMap<>();

    private final long initValue;

    public Statistics() {
        this(0);
    }

    public Statistics(long initValue) {
        this.initValue = initValue;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        //noinspection SuspiciousMethodCalls
        return map.containsKey(o);
    }

    private long get(E e) {
        return map.computeIfAbsent(e, i -> initValue);
    }

    public long count(E e) {
        return get(e);
    }

    public long total() {
        long size = 0;
        for (E e : this) {
            size = Math.addExact(size, get(e));
        }
        return size;
    }

    public long reset(E e) {
        long r = map.getOrDefault(e, initValue);
        map.put(e, initValue);
        return r;
    }

    @Override
    public boolean add(E e) {
        map.put(e, get(e) + 1);
        return true;
    }

    public boolean add(E e, long count) {
        map.put(e, Math.addExact(get(e), count));
        return true;
    }

    public boolean add(Statistics<E> other) {
        for (E key : other) {
            add(key, other.get(key));
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        map.remove(o);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) return false;
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }
}
