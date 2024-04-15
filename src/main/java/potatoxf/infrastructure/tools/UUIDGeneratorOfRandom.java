package potatoxf.infrastructure.tools;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 使用 {@link ThreadLocalRandom#current()}的{@link ThreadLocalRandom#nextBytes(byte[])}作为初始种子的{@link Random}，
 * 而不是调用 {@link UUID#randomUUID()}这在安全随机 ID 和性能之间提供了更好的平衡。
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
class UUIDGeneratorOfRandom implements UUIDGenerator {

    private final Random random;

    UUIDGeneratorOfRandom() {
        byte[] seed = new byte[8];
        ThreadLocalRandom.current().nextBytes(seed);
        this.random = new Random(new BigInteger(seed).longValue());
    }

    @Override
    public UUID generate() {
        byte[] randomBytes = new byte[16];
        this.random.nextBytes(randomBytes);

        long mostSigBits = 0;
        for (int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
        }

        long leastSigBits = 0;
        for (int i = 8; i < 16; i++) {
            leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
        }

        return new UUID(mostSigBits, leastSigBits);
    }

}
