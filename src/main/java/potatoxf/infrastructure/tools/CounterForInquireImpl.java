package potatoxf.infrastructure.tools;

import potatoxf.api.support.Com;

import java.util.concurrent.atomic.LongAdder;

/**
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
class CounterForInquireImpl implements CounterForInquire {
    private final LongAdder hitCount = new LongAdder();
    private final LongAdder missCount = new LongAdder();

    @Override
    public void recordHit(int count) {
        hitCount.add(count);
    }

    @Override
    public void recordMiss(int count) {
        missCount.add(count);
    }

    @Override
    public long hitCount() {
        return hitCount.sum();
    }

    @Override
    public long missCount() {
        return missCount.sum();
    }

    @Override
    public String toString() {
        return Com.buildToString("hitCount", hitCount(), "missCount", missCount());
    }
}
