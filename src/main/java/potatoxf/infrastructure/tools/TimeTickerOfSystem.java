package potatoxf.infrastructure.tools;

/**
 * 系统时间器
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
enum TimeTickerOfSystem implements TimeTicker {
    INSTANCE;

    @Override
    public long read() {
        return System.nanoTime();
    }
}
