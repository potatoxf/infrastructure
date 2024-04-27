package potatoxf.infrastructure.tools;

/**
 * 查询的统计器的委托
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public interface CounterForInquireDelegate extends CounterForInquire {
    /**
     * 获取代理 {@link CounterForInquire}
     *
     * @return 返回代理{@link CounterForInquire}
     */
    CounterForInquire delegateCounterForInquire();

    /**
     * 记录已命中
     *
     * @param count 记录已命中次数
     */
    @Override
    default void recordHit(int count) {
        delegateCounterForInquire().recordHit(count);
    }

    /**
     * 记录未命中
     *
     * @param count 记录未命中次数
     */
    @Override
    default void recordMiss(int count) {
        delegateCounterForInquire().recordMiss(count);
    }

    /**
     * 获取记录已命中
     *
     * @return 返回记录已命中
     */
    @Override
    default long hitCount() {
        return delegateCounterForInquire().hitCount();
    }

    /**
     * 获取记录未命中
     *
     * @return 返回记录未命中
     */
    @Override
    default long missCount() {
        return delegateCounterForInquire().missCount();
    }
}
