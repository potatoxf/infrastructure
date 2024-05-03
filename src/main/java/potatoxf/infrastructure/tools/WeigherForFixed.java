package potatoxf.infrastructure.tools;

/**
 * 固定权重
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
class WeigherForFixed implements WeigherForTwain<Object, Object>, WeigherForSingle<Object> {
    public static final WeigherForFixed ONE = new WeigherForFixed();
    private final int weigh;

    WeigherForFixed() {
        this(1);
    }

    WeigherForFixed(int weigh) {
        this.weigh = weigh;
    }

    @Override
    public int weigh(Object key, Object value) {
        return this.weigh;
    }

    @Override
    public int weigh(Object input) {
        return this.weigh;
    }
}
