package potatoxf.infrastructure.tools;

/**
 * 一个时间源，它返回一个时间值，该时间值表示自某个固定但任意的时间点以来经过的数量。
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface TimeTicker {
    /**
     * 返回 {@link System#nanoTime} 读取当前数量。
     */
    static TimeTicker systemTicker() {
        return TimeTickerOfSystem.INSTANCE;
    }

    /**
     * 始终返回 {@code 0} 的数量。
     */
    static TimeTicker disabledTicker() {
        return TimeTickerOfDisable.INSTANCE;
    }

    /**
     * 读取固定参考点以来经过的数量。
     *
     * @return 返回固定参考点以来经过的数量
     */
    long read();
}
