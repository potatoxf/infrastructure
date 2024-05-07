package potatoxf.infrastructure.value;

/**
 * Representation of MessagePack's Nil type.
 * <p/>
 * Create Time:2024-05-07
 *
 * @author potatoxf
 */
public interface NilVal extends AnyTypeVal<Void>, Value {
    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.NIL;
    }
}
