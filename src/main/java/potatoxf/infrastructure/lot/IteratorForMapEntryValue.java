package potatoxf.infrastructure.lot;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * {@link Map}的值迭代器
 * <p/>
 * Create Time:2024-04-26
 *
 * @author potatoxf
 */
public class IteratorForMapEntryValue<T> implements Iterator<T> {
    private final Iterator<? extends Map.Entry<?, T>> iterator;

    public IteratorForMapEntryValue(Map<?, T> map) {
        this(map.entrySet());
    }

    public IteratorForMapEntryValue(Set<? extends Map.Entry<?, T>> set) {
        this(set.iterator());
    }

    public IteratorForMapEntryValue(Iterator<? extends Map.Entry<?, T>> iterator) {
        this.iterator = iterator;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        return iterator.next().getValue();
    }

    /**
     * Removes from the underlying collection the last element returned
     * by this iterator (optional operation).  This method can be called
     * only once per call to {@link #next}.  The behavior of an iterator
     * is unspecified if the underlying collection is modified while the
     * iteration is in progress in any way other than by calling this
     * method.
     *
     * @throws UnsupportedOperationException if the {@code remove}
     *                                       operation is not supported by this iterator
     * @throws IllegalStateException         if the {@code next} method has not
     *                                       yet been called, or the {@code remove} method has already
     *                                       been called after the last call to the {@code next}
     *                                       method
     * @implSpec The default implementation throws an instance of
     * {@link UnsupportedOperationException} and performs no other action.
     */
    @Override
    public void remove() {
        iterator.remove();
    }
}
