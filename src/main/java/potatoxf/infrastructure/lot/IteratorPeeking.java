package potatoxf.infrastructure.lot;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
public interface IteratorPeeking<E> extends Iterator<E> {
    /**
     * 返回一个迭代器，该迭代器返回第一次迭代，然后返回第二次迭代。
     */
    static <E> IteratorPeeking<E> concat(IteratorPeeking<E> first, IteratorPeeking<E> second) {
        if (first == null && second == null) {
            //noinspection unchecked
            return (IteratorPeeking<E>) Empty.INSTANCE;
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else {
            return new Concat<>(first, second);
        }
    }

    /**
     * 返回一个迭代器，该迭代器从后备迭代器中选择较大的元素。
     */
    static <E> IteratorPeeking<E> comparing(IteratorPeeking<E> first, IteratorPeeking<E> second, Comparator<E> comparator) {
        if (first == null && second == null) {
            //noinspection unchecked
            return (IteratorPeeking<E>) Empty.INSTANCE;
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else {
            return new Comparing<>(first, second, comparator);
        }
    }

    /**
     * 返回迭代中的下一个元素，而不推进迭代。
     */
    E peek();

    enum Empty implements IteratorPeeking<Object> {
        INSTANCE;

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public Object peek() {
            throw new NoSuchElementException();
        }
    }

    class Concat<E> implements IteratorPeeking<E> {
        private final IteratorPeeking<E> first, second;

        private Concat(IteratorPeeking<E> first, IteratorPeeking<E> second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean hasNext() {
            return first.hasNext() || second.hasNext();
        }

        @Override
        public E next() {
            if (first.hasNext()) {
                return first.next();
            } else if (second.hasNext()) {
                return second.next();
            }
            throw new NoSuchElementException();
        }

        @Override
        public E peek() {
            return first.hasNext() ? first.peek() : second.peek();
        }
    }

    class Comparing<E> implements IteratorPeeking<E> {
        private final IteratorPeeking<E> first, second;
        private final Comparator<E> comparator;

        private Comparing(IteratorPeeking<E> first, IteratorPeeking<E> second, Comparator<E> comparator) {
            this.first = first;
            this.second = second;
            this.comparator = comparator;
        }

        @Override
        public boolean hasNext() {
            return first.hasNext() || second.hasNext();
        }

        @Override
        public E next() {
            if (!first.hasNext()) {
                return second.next();
            } else if (!second.hasNext()) {
                return first.next();
            }
            E o1 = first.peek();
            E o2 = second.peek();
            boolean greaterOrEqual = (comparator.compare(o1, o2) >= 0);
            return greaterOrEqual ? first.next() : second.next();
        }

        @Override
        public E peek() {
            if (!first.hasNext()) {
                return second.peek();
            } else if (!second.hasNext()) {
                return first.peek();
            }
            E o1 = first.peek();
            E o2 = second.peek();
            boolean greaterOrEqual = (comparator.compare(o1, o2) >= 0);
            return greaterOrEqual ? first.peek() : second.peek();
        }
    }
}
