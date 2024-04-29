package potatoxf.infrastructure.lot;

import java.util.function.Function;
import java.util.function.IntSupplier;

/**
 * {@link IteratorPeeking}迭代器，可以获取当前元素迭代器
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public class IteratorPeekingExtensible<E> extends IteratorExtensible<E> implements IteratorPeeking<E> {
    public IteratorPeekingExtensible(E start, Function<E, E> computor, IntSupplier changeCount) {
        super(start, computor, changeCount);
    }

    @Override
    public E peek() {
        return cursor;
    }
}