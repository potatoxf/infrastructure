package potatoxf.infrastructure.function;

/**
 * 表示接受三个输入参数进行操作
 * <p/>
 * Create Time:2024-05-05
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @author potatoxf
 */
@FunctionalInterface
public interface ThFunction<T, U, E, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param e the third function argument
     * @return the function result
     */
    R apply(T t, U u, E e);
}
