package potatoxf.infrastructure.value;


/**
 * <p/>
 * Create Time:2024-05-06
 *
 * @author potatoxf
 */
public interface ValueTypeConverter extends ValueTypeJudgment {

    /**
     * Returns the value as {@code NilVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((NilVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Nil.
     */
    default NilVal asNilVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code NilVar}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((NilVar) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Nil.
     */
    default NilVar asNilVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BooleanVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BooleanVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Boolean.
     */
    default BooleanVal asBooleanVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BooleanVar}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BooleanVar) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Boolean.
     */
    default BooleanVar asBooleanVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code NumberValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((NumberValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer or Float.
     */
    default AnyNumberVal<Number> asNumberVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code NumberVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((NumberVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer or Float.
     */
    default AnyNumberVar<Number> asNumberVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ByteVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((ByteVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Byte.
     */
    default ByteVal asByteVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ByteVar}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((ByteVar) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Byte.
     */
    default ByteVar asByteVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ShortVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((ShortVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Short.
     */
    default ShortVal asShortVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ShortVar}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((ShortVar) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Short.
     */
    default ShortVar asShortVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code IntegerValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((IntegerValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer.
     */
    default IntegerVal asIntegerVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code IntegerVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((IntegerVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer.
     */
    default IntegerVar asIntegerVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code FloatValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((FloatValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Float.
     */
    default FloatVal asFloatVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code FloatVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((FloatVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Float.
     */
    default FloatVar asFloatVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code DoubleValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((DoubleValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Double.
     */
    default DoubleVal asDoubleVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code DoubleVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((DoubleVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Double.
     */
    default DoubleVar asDoubleVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BigIntegerValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BigIntegerValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer or Float.
     */
    default BigIntegerVal asBigIntegerVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BigIntegerVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BigIntegerVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer or Float.
     */
    default BigIntegerVar asBigIntegerVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BigDecimalValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BigDecimalValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer or Float.
     */
    default BigDecimalVar asBigDecimalVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BigDecimalVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BigDecimalVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Integer or Float.
     */
    default BigDecimalVar asBigDecimalVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BinaryVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BinaryVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Binary.
     */
    default BinaryVal asBinaryVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code BinaryVar}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((BinaryVar) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Binary.
     */
    default BinaryVar asBinaryVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code StringValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((StringValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not String.
     */
    default StringVal asStringVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code StringVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((StringVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not String.
     */
    default StringVar asStringVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code TimestampValue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((TimestampValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Map.
     */
    default WhenVal asWhenVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code TimestampVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((TimestampVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Map.
     */
    default WhenVar asWhenVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ArrayVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((ArrayVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Array.
     */
    default ArrayVal asArrayVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code ArrayVarue}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((ArrayVarue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Array.
     */
    default ArrayVar asArrayVar() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code MapVal}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((MapVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Map.
     */
    default MapVal asMapVal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value as {@code MapVar}. Otherwise throws {@link UnsupportedOperationException}.
     * <p>
     * Note that you can't use <code>instanceof</code> or cast <code>((MapVar) thisValue)</code> to check type of a value because type of a mutable value is variable.
     *
     * @throws UnsupportedOperationException If type of this value is not Map.
     */
    default MapVar asMapVar() {
        throw new UnsupportedOperationException();
    }
}
