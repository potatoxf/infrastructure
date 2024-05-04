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
     * 始终返回 {@code 0} 的数量。
     */
    static TimeTicker disabledTicker() {
        return Disable.INSTANCE;
    }

    /**
     * 返回 {@link System#nanoTime} 读取当前数量。
     */
    static TimeTicker systemTicker() {
        return SystemNanoTime.INSTANCE;
    }

    /**
     * 返回 {@link System#currentTimeMillis()} 读取当前数量。
     */
    static TimeTicker systemMillisTimeticker() {
        return SystemMillisTime.INSTANCE;
    }

    /**
     * 读取固定参考点以来经过的数量。
     *
     * @return 返回固定参考点以来经过的数量
     */
    long read();

    class Disable implements TimeTicker {
        private static final TimeTicker INSTANCE = new Disable();

        private Disable() {
        }

        @Override
        public long read() {
            return 0L;
        }
    }

    class SystemNanoTime implements TimeTicker {
        private static final TimeTicker INSTANCE = new SystemNanoTime();

        private SystemNanoTime() {
        }

        @Override
        public long read() {
            return System.nanoTime();
        }
    }

    class SystemMillisTime implements TimeTicker {
        private static final TimeTicker INSTANCE = new SystemMillisTime();

        private SystemMillisTime() {
        }

        @Override
        public long read() {
            return System.currentTimeMillis();
        }
    }
}
