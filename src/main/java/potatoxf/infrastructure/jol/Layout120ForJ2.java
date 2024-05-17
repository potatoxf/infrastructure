package potatoxf.infrastructure.jol;

import potatoxf.api.support.Arg;

/**
 * 强制执行内存布局，通过填充写入计数来避免错误共享。
 * <p/>
 * Create Time:2024-05-04
 *
 * @author potatoxf
 */
public class Layout120ForJ2 extends Layout120ForJ1P {
    private static final long OFFSET_VALUE_2 = Arg.safeGetObjectFieldOffset(Layout120ForJ2.class, "value2");
    private volatile long value2;

    protected final long value2() {
        return Arg.safeGetUnsafe().getLongVolatile(this, OFFSET_VALUE_2);
    }

    protected final void value2(long count) {
        Arg.safeGetUnsafe().putOrderedLong(this, OFFSET_VALUE_2, count);
    }

    protected final boolean value2(long expect, long update) {
        return Arg.safeGetUnsafe().compareAndSwapLong(this, OFFSET_VALUE_2, expect, update);
    }
}
