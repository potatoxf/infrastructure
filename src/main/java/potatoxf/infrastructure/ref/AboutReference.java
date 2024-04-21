package potatoxf.infrastructure.ref;

import java.lang.ref.Reference;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 关于引用的扩展
 * <p>
 * <p/>
 * Create Time:2024-04-17
 *
 * @author potatoxf
 * @see BasicValReference
 * @see BasicVarReference
 * @see LookupReference
 * @see PairReference
 * @see StrongEqualsReference
 * @see StrongIdentityReference
 * @see SoftEqualsReference
 * @see SoftIdentityReference
 * @see WeakEqualsReference
 * @see WeakIdentityReference
 */
public interface AboutReference<T> extends Supplier<T> {
    /**
     * 获取引用的值
     *
     * @return 返回引用的值
     */
    T get();

    /**
     * 清除引用
     */
    void clear();

    /**
     * 相关引用，在不同实现中引用的是不同的，默认情况是{@link this}
     *
     * @return 返回相关引用
     */
    default AboutReference<?> aboutReference() {
        return this;
    }

    /**
     * 比较引用值是否相等，主要用于{@link Object#equals(Object)}
     *
     * @param other 另一个引用{@link AboutReference}或{@link Reference}
     * @return 如果相等返回true，否则返回false
     */
    default boolean referenceEquals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        Object otherReferent;
        if (other instanceof Reference) {
            otherReferent = ((Reference<?>) other).get();
        } else if (other instanceof AboutReference) {
            otherReferent = ((AboutReference<?>) other).get();
        } else {
            return false;
        }
        return Objects.equals(this.get(), otherReferent);
    }

    /**
     * 获取引用值{@link Object#hashCode()}
     *
     * @return 返回引用值 {@link Object#hashCode()}
     */
    default int referenceHashCode() {
        return Objects.hash(this.get());
    }

    /**
     * 获取引用值{@link Object#toString()}
     *
     * @return 返回引用值 {@link Object#toString()}
     */
    default String referenceToString() {
        return String.valueOf(this.get());
    }
}
