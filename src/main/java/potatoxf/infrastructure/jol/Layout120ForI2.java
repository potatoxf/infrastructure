package potatoxf.infrastructure.jol;

import potatoxf.infrastructure.Arg;

/**
 * 强制执行内存布局，通过填充写入计数来避免错误共享。
 * <p/>
 * Create Time:2024-05-04
 *
 * @author potatoxf
 */
public class Layout120ForI2 extends Layout120ForI1P {
    private static final long OFFSET_VALUE_2 = Arg.safeGetObjectFieldOffset(Layout120ForI2.class, "value2");
    private volatile int value2;

    protected final int value2() {
        return Arg.safeGetUnsafe().getIntVolatile(this, OFFSET_VALUE_2);
    }

    protected final void value2(int count) {
        Arg.safeGetUnsafe().putOrderedInt(this, OFFSET_VALUE_2, count);
    }

    protected final boolean value2(int expect, int update) {
        return Arg.safeGetUnsafe().compareAndSwapInt(this, OFFSET_VALUE_2, expect, update);
    }
}
