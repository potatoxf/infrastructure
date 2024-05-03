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
public interface WeigherForSingle<T> {
    /**
     * 返回权重为 {@code 1} 的{@link WeigherForSingle}权重计算器。
     *
     * @return 返回 {@link WeigherForSingle}
     */
    @SuppressWarnings("unchecked")
    static <T> WeigherForSingle<T> singleton() {
        return (WeigherForSingle<T>) WeigherForFixed.ONE;
    }

    /**
     * 代理{@link WeigherForSingle}，强制为非负数。
     *
     * @param delegate 被代理{@link WeigherForSingle}
     * @return 返回 {@link WeigherForSingle}
     */
    static <T> WeigherForSingle<T> boundedDelegate(WeigherForSingle<T> delegate) {
        return new Bounded<>(delegate);
    }

    /**
     * 计算权重值，权重没有单位，彼此之间是相对的。
     *
     * @param input 输入值
     * @return 返回计算后权重
     */
    int weigh(T input);

    final class Bounded<T> implements WeigherForSingle<T>, Serializable {
        private static final long serialVersionUID = 1;
        private final WeigherForSingle<? super T> delegate;

        Bounded(WeigherForSingle<? super T> delegate) {
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
