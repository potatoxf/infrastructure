package potatoxf.infrastructure.tools;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用于生成通用唯一标识符{@link UUID}
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
@FunctionalInterface
public interface UUIDGenerator {
    /**
     * JDK默认{@link UUID#randomUUID()}的 {@link UUIDGenerator}生成器。
     *
     * @return {@link UUIDGenerator}
     */
    static UUIDGenerator jdk() {
        return UUID::randomUUID;
    }

    /**
     * 一个简单的 {@link UUIDGenerator}，从1开始递增到{@link Long#MAX_VALUE}然后翻转。
     *
     * @return {@link UUIDGenerator}
     */
    static UUIDGenerator simple() {
        AtomicLong l = new AtomicLong();
        return () -> new UUID(0, l.incrementAndGet());
    }

    /**
     * 一个随机的 {@link UUIDGenerator}生成器。
     *
     * @return {@link UUIDGenerator}
     */
    static UUIDGenerator random() {
        return new UUIDGeneratorForRandom();
    }

    /**
     * 生成唯一标识符{@link UUID}
     *
     * @return 返回生成唯一标识符{@link UUID}
     */
    UUID generate();

}
