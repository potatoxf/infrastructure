package potatoxf.infrastructure.jol;

import potatoxf.infrastructure.Arg;

/**
 * 强制执行内存布局，通过填充写入计数来避免错误共享。
 * <p/>
 * Create Time:2024-05-04
 *
 * @author potatoxf
 */
public class Layout128ForI1 extends PaddingHeader128 {
    private static final long OFFSET_VALUE_1 = Arg.safeGetObjectFieldOffset(Layout128ForI1.class, "value1");
    private volatile int value1;

    protected final int value1() {
        return Arg.safeGetUnsafe().getIntVolatile(this, OFFSET_VALUE_1);
    }

    protected final void value1(int count) {
        Arg.safeGetUnsafe().putOrderedInt(this, OFFSET_VALUE_1, count);
    }

    protected final boolean value1(int expect, int update) {
        return Arg.safeGetUnsafe().compareAndSwapInt(this, OFFSET_VALUE_1, expect, update);
    }
}
