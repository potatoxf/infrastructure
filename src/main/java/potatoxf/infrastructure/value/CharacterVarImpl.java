package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public class CharacterVarImpl extends CharacterValImpl implements CharacterVar {
    protected CharacterVarImpl(Character value) {
        super(value);
    }

    @Override
    public void setValue(Character value) {
        this.value = value;
    }
}
