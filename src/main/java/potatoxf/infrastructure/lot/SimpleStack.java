package potatoxf.infrastructure.lot;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 简单的堆栈
 * <p/>
 * Create Time:2024-05-05
 *
 * @author potatoxf
 */
public final class SimpleStack<E> {
    private Object[] elements = new Object[2];
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(E e) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2 + 3);
        }
        elements[size++] = e;
    }

    public E pop() {
        Object e = elements[size];
        elements[size] = null;
        size--;
        //noinspection unchecked
        return (E) e;
    }

    public E peek() {
        if (size == 0) throw new NoSuchElementException();
        //noinspection unchecked
        return (E) elements[size - 1];
    }
}
