package potatoxf.infrastructure.value;


/**
 * Representation of MessagePack types.
 * <p>
 * MessagePack uses hierarchical type system. Integer and Float are subypte of Number, Thus {@link #isNumberType()}
 * returns true if type is Integer or Float. String and Binary are subtype of Raw. Thus {@link #isRawType()} returns
 * true if type is String or Binary.
 * <p/>
 * Create Time:2024-05-07
 *
 * @author potatoxf
 */
public enum ValueType implements ValueTypeJudgment {
    NIL(false, false),
    BOOLEAN(false, false),
    BYTE(true, false),
    SHORT(true, false),
    INTEGER(true, false),
    LONG(true, false),
    FLOAT(true, false),
    DOUBLE(true, false),
    BIG_INTEGER(true, false),
    BIG_DECIMAL(true, false),
    STRING(false, true),
    BINARY(false, true),
    ARRAY(false, false),
    MAP(false, false),
    WHEN(false, false),
    EXTENSION(false, false);

    private final boolean numberType;
    private final boolean rawType;

    ValueType(boolean numberType, boolean rawType) {
        this.numberType = numberType;
        this.rawType = rawType;
    }

    @Override
    public boolean isNilType() {
        return this == NIL;
    }

    @Override
    public boolean isBooleanType() {
        return this == BOOLEAN;
    }

    @Override
    public boolean isNumberType() {
        return this.numberType;
    }

    @Override
    public boolean isByteType() {
        return this == BYTE;
    }

    @Override
    public boolean isShortType() {
        return this == SHORT;
    }

    @Override
    public boolean isIntegerType() {
        return this == INTEGER;
    }

    @Override
    public boolean isLongType() {
        return this == LONG;
    }

    @Override
    public boolean isFloatType() {
        return this == FLOAT;
    }

    @Override
    public boolean isDoubleType() {
        return this == DOUBLE;
    }

    @Override
    public boolean isStringType() {
        return this == STRING;
    }

    @Override
    public boolean isBinaryType() {
        return this == BINARY;
    }

    @Override
    public boolean isArrayType() {
        return this == ARRAY;
    }

    @Override
    public boolean isMapType() {
        return this == MAP;
    }

    @Override
    public boolean isExtensionType() {
        return this == EXTENSION;
    }

    @Override
    public boolean isWhenType() {
        return this == WHEN;
    }

}
