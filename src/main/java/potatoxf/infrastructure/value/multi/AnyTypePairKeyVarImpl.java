package potatoxf.infrastructure.value.multi;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypePairKeyVarImpl<T, K> extends AnyTypePairKeyValImpl<T, K> implements AnyTypePairKeyVar<T, K> {
    public AnyTypePairKeyVarImpl(T type, K key) {
        super(type, key);
    }

    @Override
    public void setType(T type) {
        this.type = type;
    }

    @Override
    public void setKey(K key) {
        this.key = key;
    }
}
