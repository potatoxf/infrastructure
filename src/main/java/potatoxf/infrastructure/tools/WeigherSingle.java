package potatoxf.infrastructure.tools;

import java.io.Serializable;
import java.util.Objects;

/**
 * 单个值计算权重
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface WeigherSingle<T> {
    /**
     * 返回权重为 {@code 1} 的{@link WeigherSingle}权重计算器。
     *
     * @return 返回 {@link WeigherSingle}
     */
    @SuppressWarnings("unchecked")
    static <T> WeigherSingle<T> singleton() {
        return (WeigherSingle<T>) WeigherFixed.ONE;
    }

    /**
     * 代理{@link WeigherSingle}，强制为非负数。
     *
     * @param delegate 被代理{@link WeigherSingle}
     * @return 返回 {@link WeigherSingle}
     */
    static <T> WeigherSingle<T> boundedDelegate(WeigherSingle<T> delegate) {
        return new Bounded<>(delegate);
    }

    /**
     * 计算权重值，权重没有单位，彼此之间是相对的。
     *
     * @param input 输入值
     * @return 返回计算后权重
     */
    int weigh(T input);

    final class Bounded<T> implements WeigherSingle<T>, Serializable {
        private static final long serialVersionUID = 1;
        private final WeigherSingle<? super T> delegate;

        Bounded(WeigherSingle<? super T> delegate) {
            this.delegate = Objects.requireNonNull(delegate, "The delegate must be not null");
        }

        @Override
        public int weigh(T input) {
            int weight = delegate.weigh(input);
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
