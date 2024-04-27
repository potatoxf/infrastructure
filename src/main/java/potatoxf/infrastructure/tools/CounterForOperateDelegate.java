package potatoxf.infrastructure.tools;

/**
 * 操作的统计器的委托
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public interface CounterForOperateDelegate extends CounterForOperate {
    /**
     * 获取代理 {@link CounterForOperate}
     *
     * @return 返回代理{@link CounterForOperate}
     */
    CounterForOperate delegateCounterForOperate();

    /**
     * 记录成功操作信息
     *
     * @param time 记录成功操作所花费时间
     */
    @Override
    default void recordSuccess(long time) {
        delegateCounterForOperate().recordSuccess(time);
    }

    /**
     * 记录失败操作信息
     *
     * @param time 记录失败操作所花费时间
     */
    @Override
    default void recordFailure(long time) {
        delegateCounterForOperate().recordFailure(time);
    }

    /**
     * 获取成功次数统计
     *
     * @return 返回成功次数
     */
    @Override
    default long successCount() {
        return delegateCounterForOperate().successCount();
    }

    /**
     * 获取成功时间统计统计
     *
     * @return 返回成功时间统计
     */
    @Override
    default long successTimeCount() {
        return delegateCounterForOperate().successTimeCount();
    }

    /**
     * 获取失败次数统计
     *
     * @return 返回失败次数
     */
    @Override
    default long failureCount() {
        return delegateCounterForOperate().failureCount();
    }

    /**
     * 获取失败时间统计统计
     *
     * @return 返回失败时间统计
     */
    @Override
    default long failureTimeCount() {
        return delegateCounterForOperate().failureTimeCount();
    }

    /**
     * 获取总计次数统计
     *
     * @return 返回总计次数
     */
    @Override
    default long totalCount() {
        return delegateCounterForOperate().totalCount();
    }

    /**
     * 获取总计时间统计统计
     *
     * @return 返回总计时间统计
     */
    @Override
    default long totalTimeCount() {
        return delegateCounterForOperate().totalTimeCount();
    }
}
