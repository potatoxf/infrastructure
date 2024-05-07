package potatoxf.infrastructure.value;

/**
 * {@link byte[]}类型值
 * <p/>
 * Create Time:2024-05-07
 *
 * @author potatoxf
 */
public interface BinaryVal extends AnyTypeVal<byte[]>, Value {

    /**
     * 当前值的类型
     *
     * @return 返回值类型
     */
    @Override
    default ValueType valueType() {
        return ValueType.BINARY;
    }
}
