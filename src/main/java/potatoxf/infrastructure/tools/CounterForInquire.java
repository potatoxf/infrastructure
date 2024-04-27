package potatoxf.infrastructure.tools;

/**
 * 查询的统计器，用于记录查询是否命中次数
 * <p/>
 * Create Time:2024-04-27
 *
 * @author potatoxf
 */
public interface CounterForInquire {
    CounterForInquire DISABLE = new Disable();

    static CounterForInquire of() {
        return new CounterForInquireImpl();
    }

    /**
     * 记录已命中
     *
     * @param count 记录已命中次数
     */
    void recordHit(int count);

    /**
     * 记录未命中
     *
     * @param count 记录未命中次数
     */
    void recordMiss(int count);

    /**
     * 获取记录已命中
     *
     * @return 返回记录已命中
     */
    long hitCount();

    /**
     * 获取记录未命中
     *
     * @return 返回记录未命中
     */
    long missCount();

    class Disable implements CounterForInquire {

        private Disable() {
        }

        @Override
        public void recordHit(int count) {

        }

        @Override
        public void recordMiss(int count) {

        }

        @Override
        public long hitCount() {
            return 0;
        }

        @Override
        public long missCount() {
            return 0;
        }
    }
}
