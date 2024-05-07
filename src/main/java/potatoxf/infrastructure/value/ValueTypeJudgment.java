package potatoxf.infrastructure.value;

/**
 * <p/>
 * Create Time:2024-05-06
 *
 * @author potatoxf
 */
public interface ValueTypeJudgment {

    /**
     * Returns true if type of this value is Nil.
     * <p>
     * If this method returns true, {@code asNilValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((NilVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isNilType() {
        return false;
    }

    /**
     * Returns true if type of this value is Boolean.
     * <p>
     * If this method returns true, {@code asBooleanValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((BooleanValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isBooleanType() {
        return false;
    }

    /**
     * Returns true if type of this value is Integer or Float.
     * <p>
     * If this method returns true, {@code asNumberValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((NumberValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isNumberType() {
        return false;
    }

    /**
     * Returns true if type of this value is Byte.
     * <p>
     * If this method returns true, {@code asByteValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((ByteValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isByteType() {
        return false;
    }

    /**
     * Returns true if type of this value is Short.
     * <p>
     * If this method returns true, {@code asShortValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((ShortValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isShortType() {
        return false;
    }

    /**
     * Returns true if type of this value is Integer.
     * <p>
     * If this method returns true, {@code asIntegerValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((IntegerValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isIntegerType() {
        return false;
    }

    /**
     * Returns true if type of this value is Long.
     * <p>
     * If this method returns true, {@code asLongValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((LongValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isLongType() {
        return false;
    }

    /**
     * Returns true if type of this value is Float.
     * <p>
     * If this method returns true, {@code asFloatValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((FloatValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isFloatType() {
        return false;
    }

    /**
     * Returns true if type of this value is Double.
     * <p>
     * If this method returns true, {@code asDoubleValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((DoubleValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isDoubleType() {
        return false;
    }

    /**
     * Returns true if type of this value is Binary.
     * <p>
     * If this method returns true, {@code asBinaryValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((BinaryVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isBinaryType() {
        return false;
    }

    /**
     * Returns true if type of this value is String.
     * <p>
     * If this method returns true, {@code asStringValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((StringValue) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isStringType() {
        return false;
    }

    /**
     * Returns true if the type of this value is When.
     * <p>
     * If this method returns true, {@code asWhen} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((WhenVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isWhenType() {
        return false;
    }

    /**
     * Returns true if type of this value is Array.
     * <p>
     * If this method returns true, {@code asArrayValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((ArrayVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isArrayType() {
        return false;
    }

    /**
     * Returns true if type of this value is Map.
     * <p>
     * If this method returns true, {@code asMapValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((MapVal) thisValue)</code> to check type of a value because type of a mutable value is variable.
     */
    default boolean isMapType() {
        return false;
    }

    /**
     * Returns true if type of this an Extension.
     * <p>
     * If this method returns true, {@code asExtensionValue} never throws exceptions.
     * Note that you can't use <code>instanceof</code> or cast <code>((ExtensionValue) thisValue)</code> to check type of a value because
     * type of a mutable value is variable.
     */
    default boolean isExtensionType() {
        return false;
    }
}
