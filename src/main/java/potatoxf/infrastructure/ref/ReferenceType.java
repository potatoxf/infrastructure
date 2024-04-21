package potatoxf.infrastructure.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 引用类型
 * <pre>
 * 1. 如{@link SoftReference} 软引用，在GC报告内存不足时会被GC回收
 * 2. 如{@link WeakReference} 弱引用，在GC时发现弱引用会回收其对象
 * </pre>
 *
 * @author potatoxf
 */
public enum ReferenceType {
    /**
     * 软引用，在GC报告内存不足时会被GC回收
     */
    SOFT,
    /**
     * 弱引用，在GC时发现弱引用会回收其对象
     */
    WEAK,
}
