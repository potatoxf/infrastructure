package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-05-06
 *
 * @author potatoxf
 */
public class Nil implements NilVar, NilVal {

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public void setValue(Void value) {

    }

    @Override
    public void fromString(String value) {

    }
}
