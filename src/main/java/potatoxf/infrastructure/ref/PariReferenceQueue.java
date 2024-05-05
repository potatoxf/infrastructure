package potatoxf.infrastructure.ref;

import java.lang.ref.ReferenceQueue;

/**
 * 一对引用（key,value）的回收队列，如果是强引用则不存在回收队列即为null
 * <p/>
 * Create Time:2024-04-22
 *
 * @author potatoxf
 */
public final class PariReferenceQueue<K, V> {
    private final ReferenceQueue<K> keyReferenceQueue;
    private final ReferenceQueue<V> valueReferenceQueue;

    public PariReferenceQueue(PairReference<?, ?> referenceType) {
        this(referenceType.getKeyReferenceType(), referenceType.getValueReferenceType());
    }

    public PariReferenceQueue(PariReferenceQueue<?, ?> pariReferenceQueue) {
        this.keyReferenceQueue = pariReferenceQueue.keyReferenceQueue == null ? null : new ReferenceQueue<>();
        this.valueReferenceQueue = pariReferenceQueue.valueReferenceQueue == null ? null : new ReferenceQueue<>();
    }

    public PariReferenceQueue(ReferenceType keyReferenceType, ReferenceType valueReferenceType) {
        this.keyReferenceQueue = keyReferenceType == null ? null : new ReferenceQueue<>();
        this.valueReferenceQueue = valueReferenceType == null ? null : new ReferenceQueue<>();
    }

    /**
     * key引用队列
     */
    public ReferenceQueue<K> keyReferenceQueue() {
        return this.keyReferenceQueue;
    }

    /**
     * value引用队列
     */
    public ReferenceQueue<V> valueReferenceQueue() {
        return this.valueReferenceQueue;
    }

    /**
     * 如果密钥是弱引用垃圾回收，则返回。
     */
    public boolean isCollectKeys() {
        return this.keyReferenceQueue != null;
    }

    /**
     * 如果值是弱值或软引用垃圾回收，则返回。
     */
    public boolean isCollectValues() {
        return this.valueReferenceQueue != null;
    }
}
