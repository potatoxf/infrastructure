package potatoxf.infrastructure.lot;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;

/**
 * {@link Iterator}迭代器，可以获取当前元素迭代器，支持移除
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public class IteratorExtensibleSupport<E> extends IteratorExtensible<E> {
    protected final Consumer<E> removeElement;

    public IteratorExtensibleSupport(E start, Function<E, E> compute, IntSupplier changeCount, Consumer<E> removeElement) {
        super(start, compute, changeCount);
        this.removeElement = Objects.requireNonNull(removeElement, "The 'Remove Element' method must be not null");
    }

    @Override
    public void remove() {
        if (previous == null) {
            throw new IllegalStateException();
        }
        if (changeCount.getAsInt() != expectedModifyCount) {
            throw new ConcurrentModificationException();
        }
        removeElement.accept(previous);
        expectedModifyCount = changeCount.getAsInt();
        previous = null;
    }
}
