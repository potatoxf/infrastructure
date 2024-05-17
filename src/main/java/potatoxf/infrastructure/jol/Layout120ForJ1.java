package potatoxf.infrastructure.jol;

import potatoxf.api.support.Arg;

/**
 * 强制执行内存布局，通过填充写入计数来避免错误共享。
 * <p/>
 * Create Time:2024-05-04
 *
 * @author potatoxf
 */
public class Layout120ForJ1 extends PaddingHeader120 {
    private static final long OFFSET_VALUE_1 = Arg.safeGetObjectFieldOffset(Layout120ForJ1.class, "value1");
    private volatile long value1;

    protected final long value1() {
        return Arg.safeGetUnsafe().getLongVolatile(this, OFFSET_VALUE_1);
    }

    protected final void value1(long count) {
        Arg.safeGetUnsafe().putOrderedLong(this, OFFSET_VALUE_1, count);
    }

    protected final boolean value1(long expect, long update) {
        return Arg.safeGetUnsafe().compareAndSwapLong(this, OFFSET_VALUE_1, expect, update);
    }
}
