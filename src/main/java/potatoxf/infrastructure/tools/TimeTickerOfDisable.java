package potatoxf.infrastructure.tools;

/**
 * 关闭时间器
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
enum TimeTickerOfDisable implements TimeTicker {
    INSTANCE;

    @Override
    public long read() {
        return 0L;
    }
}
