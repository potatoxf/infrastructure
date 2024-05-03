package potatoxf.infrastructure.tools;

import java.io.Serializable;
import java.util.Objects;

/**
 * 双个值计算权重
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface WeigherForTwain<K, V> {
    /**
     * 返回权重为 {@code 1} 的{@link WeigherForTwain}权重计算器。
     *
     * @return 返回 {@link WeigherForTwain}
     */
    @SuppressWarnings("unchecked")
    static <K, V> WeigherForTwain<K, V> singleton() {
        return (WeigherForTwain<K, V>) WeigherForFixed.ONE;
    }

    /**
     * 代理{@link WeigherForTwain}，强制为非负数。
     *
     * @param delegate 被代理{@link WeigherForTwain}
     * @return 返回 {@link WeigherForTwain}
     */
    static <K, V> WeigherForTwain<K, V> boundedDelegate(WeigherForTwain<K, V> delegate) {
        return new Bounded<>(delegate);
    }

    /**
     * 计算权重值，权重没有单位，彼此之间是相对的。
     *
     * @param key   键
     * @param value 值
     * @return 返回计算后权重
     */
    int weigh(K key, V value);

    final class Bounded<K, V> implements WeigherForTwain<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        private final WeigherForTwain<? super K, ? super V> delegate;

        private Bounded(WeigherForTwain<? super K, ? super V> delegate) {
            this.delegate = Objects.requireNonNull(delegate, "The delegate must be not null");
        }

        @Override
        public int weigh(K key, V value) {
            int weight = delegate.weigh(key, value);
            if (weight < 0) {
                throw new IllegalArgumentException("The weight must greater then 0,but the value is '" + weight + "'");
            }
            return weight;
        }

        Object writeReplace() {
            return delegate;
        }
    }
}
