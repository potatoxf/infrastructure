package potatoxf.infrastructure.tools;

import potatoxf.infrastructure.Arg;

import java.util.StringJoiner;

/**
 * 实现 {@link InterveningSequence}，增加每个间隔时间。
 * 当间隔达到 {@link #maxInterval}，则不再增加。
 * 停止重试 {@link #maxElapsedTime 最大经过时间}已达到。
 * <p/>
 * 示例：默认间隔为2000ms;默认乘数为1.5;和默认最大值间隔为30000。对于 10 次尝试，序列将是如下：
 * <pre>
 *  1              2000
 *  2              3000
 *  3              4500
 *  4              6750
 *  5             10125
 *  6             15187
 *  7             22780
 *  8             30000
 *  9             30000
 * 10             30000
 * </pre>
 * <p/>
 * 请注意，默认的最大运行时间为 {@link Long#MAX_VALUE}，并且默认最大尝试次数为 {@link Integer#MAX_VALUE}。
 * 使用 {@link #maxElapsedTime} 限制实例的时间长度应该在返回 {@link Execution#STOP} 之前累积。
 * 或者使用 {@link #maxAttempts} 限制尝试次数。执行当达到这两个限制中的任何一个时停止。
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
class InterveningSequenceOfExponent implements InterveningSequence {
    /**
     * 初始间隔（以毫秒为单位）。
     */
    private final long initialInterval;
    /**
     * 每次重试的当前间隔乘以的值。
     */
    private final double multiplier;
    /**
     * 设置最大回退时间（以毫秒为单位）。
     */
    private final long maxInterval;
    /**
     * 最大经过的时间（以毫秒为单位）
     */
    private final long maxElapsedTime;
    /**
     * 调用的最大尝试次数
     */
    private final int maxAttempts;

    InterveningSequenceOfExponent() {
        this(2000L, 1.5);
    }

    InterveningSequenceOfExponent(long initialInterval, double multiplier) {
        this(initialInterval, multiplier, 30000L, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    InterveningSequenceOfExponent(long initialInterval, double multiplier, long maxInterval, long maxElapsedTime, int maxAttempts) {
        Arg.check(multiplier >= 1, () -> "Invalid multiplier '" + multiplier + "'. Should be greater than or equal to 1. A multiplier of 1 is equivalent to a fixed interval.");
        this.initialInterval = initialInterval;
        this.multiplier = multiplier;
        this.maxInterval = maxInterval;
        this.maxElapsedTime = maxElapsedTime;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public Execution start() {
        return new ExponentialExecution();
    }

    @Override
    public int maxAttempts() {
        return maxAttempts;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "ExponentialBackOff{", "}")
                .add("initialInterval=" + initialInterval)
                .add("multiplier=" + multiplier)
                .add("maxInterval=" + maxInterval)
                .add("maxElapsedTime=" + maxElapsedTime)
                .add("maxAttempts=" + maxAttempts)
                .toString();
    }

    private class ExponentialExecution implements Execution {
        private long currentInterval = -1, currentElapsedTime = 0;
        private int attempts;

        @Override
        public long next() {
            if (currentElapsedTime >= maxElapsedTime || attempts >= maxElapsedTime) return STOP;
            long nextInterval;
            if (currentInterval >= maxInterval) {
                nextInterval = maxInterval;
            } else if (currentInterval < 0) {
                nextInterval = currentInterval = Math.min(initialInterval, maxInterval);
            } else {
                nextInterval = currentInterval = Math.min((long) (currentInterval * multiplier), maxInterval);
            }
            currentElapsedTime += nextInterval;
            attempts++;
            return nextInterval;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", "ExponentialBackOffExecution{", "}")
                    .add("currentInterval=" + (currentInterval < 0 ? "n/a" : currentInterval + "ms"))
                    .add("multiplier=" + multiplier)
                    .add("attempts=" + attempts)
                    .toString();
        }
    }
}
