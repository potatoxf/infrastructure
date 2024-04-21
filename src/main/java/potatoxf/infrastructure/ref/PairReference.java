package potatoxf.infrastructure.ref;

import lombok.Getter;

import java.lang.ref.ReferenceQueue;
import java.util.Objects;

/**
 * 一对引用，里面包含key和value的引用，他们构造引用类型为{@link #PairReference(ReferenceType, ReferenceType)}
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 */
@Getter
public final class PairReference<K, V> implements AboutReference<V> {
    private static final AboutReference<?> KEY_RETIRED = new LookupReference<>(null);
    private static final AboutReference<?> KEY_DEAD = new LookupReference<>(null);
    private final ReferenceType keyReferenceType;
    private final ReferenceType valueReferenceType;
    private volatile AboutReference<K> keyReference;
    private volatile AboutReference<V> valueReference;

    public PairReference(PairReference<?, ?> referenceType) {
        this(referenceType.keyReferenceType, referenceType.valueReferenceType);

    }

    public PairReference(ReferenceType keyReferenceType, ReferenceType valueReferenceType) {
        this.keyReferenceType = keyReferenceType;
        this.valueReferenceType = valueReferenceType;
        this.keyReference = null;
        this.valueReference = null;
    }

    /**
     * 复制新带有键值引用类型
     *
     * @return {@link PairReference}
     */
    public PairReference<K, V> copyWithType() {
        return new PairReference<>(this);
    }

    /**
     * 获取键
     *
     * @return {@link K}
     */
    public K getKey() {
        return this.keyReference == null ? null : this.keyReference.get();
    }

    /**
     * 设置键
     *
     * @param key {@link K}
     * @return {@link this}
     */
    public PairReference<K, V> setKey(K key) {
        return this.setKey(key, null);
    }

    /**
     * 设置键
     *
     * @param key   {@link K}
     * @param queue {@link ReferenceQueue}引用队列，可以为null
     * @return {@link this}
     */
    public PairReference<K, V> setKey(K key, ReferenceQueue<K> queue) {
        Objects.requireNonNull(key, "The key must be not null");
        AboutReference<K> reference = this.keyReference;
        if (isStrongKey()) {
            this.keyReference = new StrongIdentityReference<>(key);
        } else if (isSoftKey()) {
            this.keyReference = new SoftIdentityReference<>(key, queue);
        } else {
            this.keyReference = new WeakIdentityReference<>(key, queue);
        }
        if (reference != null) {
            reference.clear();
        }
        return this;
    }

    /**
     * 获取值
     *
     * @return {@link V}
     */
    public V getValue() {
        return this.valueReference == null ? null : this.valueReference.get();
    }

    /**
     * 设置值
     *
     * @param value {@link V}
     * @return {@link this}
     */
    public PairReference<K, V> setValue(V value) {
        return this.setValue(value, null);
    }

    /**
     * 设置值
     *
     * @param value {@link V}
     * @param queue {@link ReferenceQueue}引用队列，可以为null
     * @return {@link this}
     */
    public PairReference<K, V> setValue(V value, ReferenceQueue<V> queue) {
        Objects.requireNonNull(value, "The value must be not null");
        AboutReference<V> reference = this.valueReference;
        if (isStrongValue()) {
            this.valueReference = new ValueStrongIdentityReference(value);
        } else if (isSoftValue()) {
            this.valueReference = new ValueSoftIdentityReference(value, queue);
        } else {
            this.valueReference = new ValueWeakIdentityReference(value, queue);
        }
        if (reference != null) {
            reference.clear();
        }
        return this;
    }

    /**
     * 如果该条目在哈希表和页面替换策略中可用。
     */
    public boolean isAlive() {
        return this.keyReference != null && this.keyReference != KEY_RETIRED && this.keyReference != KEY_DEAD;
    }

    /**
     * 如果条目已从哈希表中删除，并且正在等待从页面中删除更换政策。
     */
    public boolean isRetired() {
        return this.keyReference == null || this.keyReference == KEY_RETIRED;
    }

    /**
     * 如果条目已从哈希表和页面替换策略中删除。
     */
    public boolean isDead() {
        return this.keyReference == null || this.keyReference == KEY_DEAD;
    }

    /**
     * 是否是强引用键
     *
     * @return 如果是返回true，否则返回false
     */
    public boolean isStrongKey() {
        return this.keyReferenceType == null;
    }

    /**
     * 是否是强引用值
     *
     * @return 如果是返回true，否则返回false
     */
    public boolean isStrongValue() {
        return this.valueReferenceType == null;
    }

    /**
     * 是否是软引用键
     *
     * @return 如果是返回true，否则返回false
     */
    public boolean isSoftKey() {
        return this.keyReferenceType == ReferenceType.SOFT;
    }

    /**
     * 是否是软引用值
     *
     * @return 如果是返回true，否则返回false
     */
    public boolean isSoftValue() {
        return this.valueReferenceType == ReferenceType.SOFT;
    }

    /**
     * 是否是弱引用键
     *
     * @return 如果是返回true，否则返回false
     */
    public boolean isWeakKey() {
        return this.keyReferenceType == ReferenceType.WEAK;
    }

    /**
     * 是否是弱引用值
     *
     * @return 如果是返回true，否则返回false
     */
    public boolean isWeakValue() {
        return this.valueReferenceType == ReferenceType.WEAK;
    }

    /**
     * 将引用设置为{@link #KEY_RETIRED}的状态
     */
    @SuppressWarnings("unchecked")
    public void retire() {
        if (isStrongValue()) {
            if (this.keyReference != null) {
                this.keyReference.clear();
            }
            this.keyReference = null;
        } else {
            clear();
        }
        this.keyReference = (AboutReference<K>) KEY_RETIRED;
    }

    /**
     * 将引用设置为{@link #KEY_DEAD}的状态
     */
    @SuppressWarnings("unchecked")
    public void die() {
        clear();
        this.keyReference = (AboutReference<K>) KEY_DEAD;
    }

    /**
     * 获取引用的值
     *
     * @return 返回引用的值
     */
    @Override
    public V get() {
        return getValue();
    }

    /**
     * 清除引用
     */
    @Override
    public void clear() {
        AboutReference<K> keyRef = this.keyReference;
        AboutReference<V> valueRef = this.valueReference;
        if (keyRef != null) {
            keyRef.clear();
        }
        if (valueRef != null) {
            valueRef.clear();
        }
        this.valueReference = null;
        this.keyReference = null;
    }

    @Override
    public boolean equals(Object other) {
        return this.referenceEquals(other);
    }

    @Override
    public int hashCode() {
        return this.referenceHashCode();
    }

    @Override
    public String toString() {
        return this.referenceToString();
    }

    private class ValueStrongIdentityReference extends StrongIdentityReference<V> {
        public ValueStrongIdentityReference(V referent) {
            super(referent);
        }

        @Override
        public AboutReference<K> aboutReference() {
            return keyReference;
        }
    }

    private class ValueSoftIdentityReference extends SoftIdentityReference<V> {
        public ValueSoftIdentityReference(V referent, ReferenceQueue<? super V> queue) {
            super(referent, queue);
        }

        @Override
        public AboutReference<K> aboutReference() {
            return keyReference;
        }
    }

    private class ValueWeakIdentityReference extends WeakIdentityReference<V> {
        public ValueWeakIdentityReference(V referent, ReferenceQueue<? super V> queue) {
            super(referent, queue);
        }

        @Override
        public AboutReference<K> aboutReference() {
            return keyReference;
        }
    }
}
