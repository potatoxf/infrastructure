package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class AnyNumberValImpl<T extends Number> extends AnyTypeValImpl<T> implements AnyNumberVal<T> {
    protected AnyNumberValImpl(T value) {
        super(value);
    }
}
