package potatoxf.infrastructure.lot;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntSupplier;

/**
 * {@link Iterator}迭代器，可以获取当前元素迭代器
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public class IteratorExtensible<E> implements Iterator<E> {
    protected E previous, cursor;
    protected final Function<E, E> compute;
    protected final IntSupplier changeCount;
    protected int expectedModifyCount;

    public IteratorExtensible(E start, Function<E, E> computor, IntSupplier changeCount) {
        this.cursor = start;
        this.compute = Objects.requireNonNull(computor, "The next element computor must be not null");
        this.changeCount = Objects.requireNonNull(changeCount, "The 'Change Count' method must be not null");
        this.expectedModifyCount = changeCount.getAsInt();
    }

    @Override
    public boolean hasNext() {
        if (changeCount.getAsInt() != expectedModifyCount) {
            throw new ConcurrentModificationException();
        }
        return cursor != null;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        previous = cursor;
        cursor = compute.apply(previous);
        return previous;
    }
}
