package potatoxf.infrastructure.tools;

/**
 * 操作的统计器，用于记录操作成功或操作失败
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public interface CounterForOperate {
    CounterForOperate DISABLE = new Disable();

    static CounterForOperate of() {
        return new CounterForOperateImpl();
    }

    /**
     * 记录成功操作信息
     *
     * @param time 记录成功操作所花费时间
     */
    void recordSuccess(long time);

    /**
     * 记录失败操作信息
     *
     * @param time 记录失败操作所花费时间
     */
    void recordFailure(long time);

    /**
     * 获取成功次数统计
     *
     * @return 返回成功次数
     */
    long successCount();

    /**
     * 获取成功时间统计统计
     *
     * @return 返回成功时间统计
     */
    long successTimeCount();

    /**
     * 获取失败次数统计
     *
     * @return 返回失败次数
     */
    long failureCount();

    /**
     * 获取失败时间统计统计
     *
     * @return 返回失败时间统计
     */
    long failureTimeCount();

    /**
     * 获取总计次数统计
     *
     * @return 返回总计次数
     */
    default long totalCount() {
        return successCount() + failureCount();
    }

    /**
     * 获取总计时间统计统计
     *
     * @return 返回总计时间统计
     */
    default long totalTimeCount() {
        return successTimeCount() + failureTimeCount();
    }

    class Disable implements CounterForOperate {
        private Disable() {
        }

        @Override
        public void recordSuccess(long time) {
        }

        @Override
        public void recordFailure(long time) {
        }

        @Override
        public long successCount() {
            return 0;
        }

        @Override
        public long successTimeCount() {
            return 0;
        }

        @Override
        public long failureCount() {
            return 0;
        }

        @Override
        public long failureTimeCount() {
            return 0;
        }
    }
}
