package potatoxf.infrastructure.tools;

/**
 * 固定间隔 {@link InterveningSequence} 序列实现，提供固定间隔在两次尝试和最大重试次数之间。
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
class InterveningSequenceForFixation implements InterveningSequence {
    /**
     * 两次尝试之间的间隔（以毫秒为单位）。
     */
    private final long interval;
    /**
     * 最大尝试次数，{@link Integer#MAX_VALUE}表示无限次尝试。
     */
    private final int maxAttempts;

    InterveningSequenceForFixation() {
        this(5000, Integer.MAX_VALUE);
    }

    InterveningSequenceForFixation(long interval, int maxAttempts) {
        this.interval = interval;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public Execution start() {
        return new FixedExecution();
    }

    @Override
    public int maxAttempts() {
        return maxAttempts;
    }

    private class FixedExecution implements Execution {
        private long currentAttempts = 0;

        @Override
        public long next() {
            return ++currentAttempts <= maxAttempts ? interval : STOP;
        }

        @Override
        public String toString() {
            return "FixedBackOff{interval=" + interval + ", currentAttempts=" + currentAttempts +
                    ", maxAttempts=" + (maxAttempts == Long.MAX_VALUE ? "unlimited" : maxAttempts) + '}';
        }
    }
}
