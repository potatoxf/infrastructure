package potatoxf.infrastructure.tools;

import potatoxf.infrastructure.Log;
import potatoxf.infrastructure.function.ThrowingCallable;
import potatoxf.infrastructure.function.ThrowingRunnable;

/**
 * 提供 {@link Execution}，指示应间隔序列操作。
 * <p/>
 * 此界面的用户应像这样使用它：
 * {@link InterveningSequence#execute(InterveningSequence, ThrowingRunnable)}
 * {@link InterveningSequence#execute(InterveningSequence, ThrowingCallable)}
 * <p/>
 * 基础操作成功完成后.可以丢弃执行实例。
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
public interface InterveningSequence {
    /**
     * {@link Execution#next()} 的返回值，指示操作不应重试。
     */
    long STOP = -1;

    /**
     * 创建指数变化时间间隔序列
     *
     * @return {@code InterveningSequence}
     */
    static InterveningSequence exponential() {
        return new InterveningSequenceOfExponent();
    }

    /**
     * 创建指数变化时间间隔序列
     *
     * @param initialInterval 初始间隔（以毫秒为单位）
     * @param multiplier      每次重试的当前间隔乘以的值。
     * @return {@code InterveningSequence}
     */
    static InterveningSequence exponential(long initialInterval, double multiplier) {
        return new InterveningSequenceOfExponent(initialInterval, multiplier);
    }

    /**
     * 创建指数变化时间间隔序列
     *
     * @param initialInterval 初始间隔（以毫秒为单位）
     * @param multiplier      每次重试的当前间隔乘以的值。
     * @param maxInterval     设置最大回退时间（以毫秒为单位）。
     * @param maxElapsedTime  最大经过的时间（以毫秒为单位）
     * @param maxAttempts     调用的最大尝试次数
     * @return {@code InterveningSequence}
     */
    static InterveningSequence exponential(long initialInterval, double multiplier, long maxInterval, long maxElapsedTime, int maxAttempts) {
        return new InterveningSequenceOfExponent(initialInterval, multiplier, maxInterval, maxElapsedTime, maxAttempts);
    }

    /**
     * 创建固定变化时间间隔序列
     *
     * @return {@code InterveningSequence}
     */
    static InterveningSequence fixed() {
        return new InterveningSequenceOfFixation();
    }

    /**
     * 创建固定变化时间间隔序列
     *
     * @param interval    两次尝试之间的间隔（以毫秒为单位）。
     * @param maxAttempts 最大尝试次数，{@link Integer#MAX_VALUE}表示无限次尝试。
     * @return {@code InterveningSequence}
     */
    static InterveningSequence fixed(long interval, int maxAttempts) {
        return new InterveningSequenceOfFixation(interval, maxAttempts);
    }

    /**
     * 按照指定间隔序列尝试执行指定逻辑
     *
     * @param interveningSequence 可后退的器
     * @param func                执行函数
     * @return 如果执行成功返回true，否则返回false
     */
    static boolean execute(InterveningSequence interveningSequence, ThrowingRunnable<Throwable> func) {
        Execution execution = interveningSequence.start();
        long waitInterval = execution.next();
        while (waitInterval != InterveningSequence.STOP) {
            try {
                func.execute();
                return true;
            } catch (Throwable e) {
                waitInterval = execution.next();
                if (Log.isEnabledWarn()) {
                    Log.warn("Error to execute," + (waitInterval == InterveningSequence.STOP
                            ? "The maximum number of times has been reached: " + interveningSequence.maxAttempts() + "]."
                            : "Try to do this at the next interval between " + waitInterval + "]."), e);
                }
            }
        }
        return waitInterval != InterveningSequence.STOP;
    }

    /**
     * 按照指定间隔序列尝试执行指定逻辑
     *
     * @param interveningSequence 可后退的器
     * @param func                执行函数
     * @return 如果执行成功返回true，否则返回false
     */
    static <V> V execute(InterveningSequence interveningSequence, ThrowingCallable<V, Throwable> func) throws Exception {
        Execution execution = interveningSequence.start();
        long waitInterval = execution.next();
        while (waitInterval != InterveningSequence.STOP) {
            try {
                return func.execute();
            } catch (Throwable e) {
                waitInterval = execution.next();
                if (Log.isEnabledWarn()) {
                    Log.warn("Error to execute," + (waitInterval == InterveningSequence.STOP
                            ? "The maximum number of times has been reached: " + interveningSequence.maxAttempts() + "]."
                            : "Try to do this at the next interval between " + waitInterval + "]."), e);
                }
            }
        }
        return null;
    }

    /**
     * Start a new back off execution.
     *
     * @return a fresh {@link Execution} ready to be used
     */
    Execution start();

    /**
     * 调用的最大尝试次数
     */
    int maxAttempts();

    /**
     * 表示特定的回退执行。
     */
    @FunctionalInterface
    interface Execution {
        /**
         * 返回重试等待毫秒数或 {@link #STOP}停止重试。
         *
         * @return 返回毫秒数或 {@link #STOP}
         */
        long next();
    }
}
