package potatoxf.infrastructure.tools;

/**
 * 固定权重
 * <p/>
 * Create Time:2024-04-20
 *
 * @author potatoxf
 */
class WeigherFixed implements WeigherTwain<Object, Object>, WeigherSingle<Object> {
    public static final WeigherFixed ONE = new WeigherFixed();
    private final int weigh;

    WeigherFixed() {
        this(1);
    }

    WeigherFixed(int weigh) {
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
