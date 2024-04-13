package potatoxf.infrastructure.value.multi;

/**
 * <p/>
 * Create Time:2024-04-13
 *
 * @author potatoxf
 */
class AnyTypeTripleKeyVarImpl<C, T, K> extends AnyTypeTripleKeyValImpl<C, T, K> implements AnyTypeTripleKeyVar<C, T, K> {
    public AnyTypeTripleKeyVarImpl(C category, T type, K key) {
        super(category, type, key);
    }

    @Override
    public void setCategory(C category) {
        this.category = category;
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
