package potatoxf.infrastructure.function;


/**
 * 抛出异常的运行函数
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Throwable> {
    /**
     * 执行函数
     *
     * @throws E 抛出异常
     */
    void execute() throws E;
}
