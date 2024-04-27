package potatoxf.infrastructure.tools;

import potatoxf.infrastructure.Com;

import java.util.concurrent.atomic.LongAdder;

/**
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
class CounterForOperateImpl implements CounterForOperate {
    private final LongAdder successCount = new LongAdder();
    private final LongAdder successTimeCount = new LongAdder();
    private final LongAdder failureCount = new LongAdder();
    private final LongAdder failureTimeCount = new LongAdder();

    @Override
    public void recordSuccess(long time) {
        successCount.add(time);
        successTimeCount.add(time);
    }

    @Override
    public void recordFailure(long time) {
        failureCount.increment();
        failureTimeCount.add(time);
    }

    @Override
    public long successCount() {
        return successCount.sum();
    }

    @Override
    public long successTimeCount() {
        return successTimeCount.sum();
    }

    @Override
    public long failureCount() {
        return failureCount.sum();
    }

    @Override
    public long failureTimeCount() {
        return failureTimeCount.sum();
    }

    @Override
    public String toString() {
        return Com.buildToString("successCount", successCount(), "successTimeCount", successTimeCount(),
                "failureCount", failureCount(), "failureTimeCount", failureTimeCount());
    }
}
