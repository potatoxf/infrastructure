package potatoxf.infrastructure.function;

/**
 * 表示接受三个输入参数进行操作
 * <p/>
 * Create Time:2024-05-05
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @author potatoxf
 */
@FunctionalInterface
public interface ThConsumer<T, U, E> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param e the third function argument
     */
    void accept(T t, U u, E e);
}
