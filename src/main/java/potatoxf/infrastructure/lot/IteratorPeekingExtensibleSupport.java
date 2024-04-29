package potatoxf.infrastructure.lot;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;

/**
 * {@link IteratorPeeking}迭代器，可以获取当前元素迭代器，支持移除
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public class IteratorPeekingExtensibleSupport<E> extends IteratorExtensibleSupport<E> implements IteratorPeeking<E> {
    public IteratorPeekingExtensibleSupport(E start, Function<E, E> compute, IntSupplier changeCount, Consumer<E> removeElement) {
        super(start, compute, changeCount, removeElement);
    }

    @Override
    public E peek() {
        return cursor;
    }
}