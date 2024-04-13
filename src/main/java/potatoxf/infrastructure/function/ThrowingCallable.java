package potatoxf.infrastructure.function;


/**
 * 抛出异常有返回值的运行函数
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface ThrowingCallable<R, E extends Throwable> {

    /**
     * 执行函数
     *
     * @return 返回值
     * @throws E 抛出异常
     */
    R execute() throws E;
}
