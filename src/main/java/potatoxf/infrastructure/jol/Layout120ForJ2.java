package potatoxf.infrastructure.jol;

import potatoxf.infrastructure.Com;

/**
 * 强制执行内存布局，通过填充写入计数来避免错误共享。
 * <p/>
 * Create Time:2024-05-04
 *
 * @author potatoxf
 */
public class Layout120ForJ2 extends Layout120ForJ1P {
    private static final long OFFSET_VALUE_2 = Com.safeGetObjectFieldOffset(Layout120ForJ2.class, "value2");
    private volatile long value2;

    protected final long value2() {
        return Com.safeGetUnsafe().getLongVolatile(this, OFFSET_VALUE_2);
    }

    protected final void value2(long count) {
        Com.safeGetUnsafe().putOrderedLong(this, OFFSET_VALUE_2, count);
    }

    protected final boolean value2(long expect, long update) {
        return Com.safeGetUnsafe().compareAndSwapLong(this, OFFSET_VALUE_2, expect, update);
    }
}
