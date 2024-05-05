package potatoxf.infrastructure;

import potatoxf.infrastructure.function.ArrayElementGetter;
import potatoxf.infrastructure.function.ArrayElementSetter;
import potatoxf.infrastructure.function.ArrayLengthGetter;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * 参数工具类
 * <p/>
 * {@code isNoNull}判断对象不为null
 * {@code isNoEmpty}判断字符串、集合、数组不为空
 * {@code isElementsNoNull}判断集合、数组不含有为null的元素
 * {@code isElementsNoEmpty}判断集合、数组不含有为空的元素
 * {@code isPeriphery}判断子字符串大小必须小于指定字符串大小
 * {@code isContains}判断某集合是否包含子集
 * {@code isMatch}判断是否匹配自定义条件
 * {@code isNoNegative}判断值是否非负数{@code value>=0}
 * {@code isNatural}判断值是否是自然数{@code value>=0}并且{@code value<limit}
 * {@code isRange}判断值是否在范围内{@code value>=lo}并且{@code value<hi}
 * {@code isExcludeRange}判断值是否排除在范围内{@code value<lo}并且{@code value>=hi}
 * {@code isBlankChar}判断是否是空字符
 * {@code isAsciiChar}判断是否是Ascii字符
 * {@code isAsciiPrintableChar}判断是否是Ascii可见字符
 * {@code isAsciiControlChar}判断是否是Ascii控制字符
 * {@code isLetterChar}判断是否是英文字符
 * {@code isNumberChar}判断是否是数字字符
 * {@code isLetterOrNumberChar}判断是否是英文字符或数字字符
 * {@code isEqualChar}判断两个字符是否相同
 * {@code isEqualChars}判断两个字符数组是否相等
 * {@code isPathSeparatorChar}判断是否是路径分割字符
 * {@code isAvailableClassName}判断是否是有效类名
 * {@code isWrapperType}判断是否是包装类型
 * {@code isCompatibilityFrom}判断类是否兼容另一个类
 * <p/>
 * Create Time:2024-04-04
 *
 * @author potatoxf
 */
public final class Arg {
    public static final Class<?> CLASS_OBJECT = Object.class, CLASSES_OBJECT = Object[].class;
    public static final Class<?> CLASS_TYPE = Class.class, CLASSES_TYPE = Class[].class;
    public static final Class<?> CLASS_MAP = Map.class, CLASS_LIST = List.class, CLASS_SET = Set.class, CLASS_NUMBER = Number.class;
    public static final Class<?> CLASS_Z = boolean.class, CLASS_WZ = Boolean.class, CLASSES_Z = boolean[].class, CLASSES_WZ = Boolean[].class;
    public static final Class<?> CLASS_C = char.class, CLASS_WC = Character.class, CLASSES_C = char[].class, CLASSES_WC = Character[].class;
    public static final Class<?> CLASS_B = byte.class, CLASS_WB = Byte.class, CLASSES_B = byte[].class, CLASSES_WB = Byte[].class;
    public static final Class<?> CLASS_S = short.class, CLASS_WS = Short.class, CLASSES_S = short[].class, CLASSES_WS = Short[].class;
    public static final Class<?> CLASS_I = int.class, CLASS_WI = Integer.class, CLASSES_I = int[].class, CLASSES_WI = Integer[].class;
    public static final Class<?> CLASS_J = long.class, CLASS_WJ = Long.class, CLASSES_J = long[].class, CLASSES_WJ = Long[].class;
    public static final Class<?> CLASS_F = float.class, CLASS_WF = Float.class, CLASSES_F = float[].class, CLASSES_WF = Float[].class;
    public static final Class<?> CLASS_D = double.class, CLASS_WD = Double.class, CLASSES_D = double[].class, CLASSES_WD = Double[].class;
    public static final boolean DEFAULT_Z = false;
    public static final char DEFAULT_C = '\0';
    public static final byte DEFAULT_B = 0;
    public static final short DEFAULT_S = 0;
    public static final int DEFAULT_I = 0;
    public static final long DEFAULT_J = 0L;
    public static final float DEFAULT_F = 0F;
    public static final double DEFAULT_D = 0D;
    private static final Map<Class<?>, BasicType> BASIC_TYPE_INFO;
    private static final Map<Class<?>, ArrayType<?, ?, ?>> ARRAY_TYPE_INFO;
    private static final BasicType NULL_BASIC_TYPE = new BasicType(-1, null, null, null);

    static {
        Map<Class<?>, BasicType> basicTypeInfo = new HashMap<>(16, 1);
        basicTypeInfo.put(CLASS_Z, new BasicType(0, CLASS_WZ, DEFAULT_Z, Boolean::parseBoolean));
        basicTypeInfo.put(CLASS_WZ, new BasicType(0, CLASS_Z, DEFAULT_Z, Boolean::parseBoolean));
        basicTypeInfo.put(CLASS_C, new BasicType(0, CLASS_WC, DEFAULT_C, s -> s.isEmpty() ? '\0' : s.charAt(0)));
        basicTypeInfo.put(CLASS_WC, new BasicType(0, CLASS_C, DEFAULT_C, s -> s.isEmpty() ? '\0' : s.charAt(0)));
        basicTypeInfo.put(CLASS_B, new BasicType(1, CLASS_WB, DEFAULT_B, Byte::parseByte));
        basicTypeInfo.put(CLASS_WB, new BasicType(1, CLASS_B, DEFAULT_B, Byte::parseByte));
        basicTypeInfo.put(CLASS_S, new BasicType(2, CLASS_WS, DEFAULT_S, Short::parseShort));
        basicTypeInfo.put(CLASS_WS, new BasicType(2, CLASS_S, DEFAULT_S, Short::parseShort));
        basicTypeInfo.put(CLASS_I, new BasicType(3, CLASS_WI, DEFAULT_I, Integer::parseInt));
        basicTypeInfo.put(CLASS_WI, new BasicType(3, CLASS_I, DEFAULT_I, Integer::parseInt));
        basicTypeInfo.put(CLASS_J, new BasicType(4, CLASS_WJ, DEFAULT_J, Long::parseLong));
        basicTypeInfo.put(CLASS_WJ, new BasicType(4, CLASS_J, DEFAULT_J, Long::parseLong));
        basicTypeInfo.put(CLASS_F, new BasicType(5, CLASS_WF, DEFAULT_F, Float::parseFloat));
        basicTypeInfo.put(CLASS_WF, new BasicType(5, CLASS_F, DEFAULT_F, Float::parseFloat));
        basicTypeInfo.put(CLASS_D, new BasicType(6, CLASS_WD, DEFAULT_D, Double::parseDouble));
        basicTypeInfo.put(CLASS_WD, new BasicType(6, CLASS_D, DEFAULT_D, Double::parseDouble));
        BASIC_TYPE_INFO = Collections.unmodifiableMap(basicTypeInfo);

        ArrayType<boolean[], Boolean[], Boolean> booleanArrayType = new ArrayType<>(boolean[]::new, Boolean[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Boolean old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Boolean[] r = new Boolean[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            boolean[] r = new boolean[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(boolean.class) : a[i];
            return r;
        });
        ArrayType<char[], Character[], Character> characterArrayType = new ArrayType<>(char[]::new, Character[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Character old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Character[] r = new Character[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            char[] r = new char[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(char.class) : a[i];
            return r;
        });
        ArrayType<byte[], Byte[], Byte> byteArrayType = new ArrayType<>(byte[]::new, Byte[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Byte old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Byte[] r = new Byte[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            byte[] r = new byte[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(byte.class) : a[i];
            return r;
        });
        ArrayType<short[], Short[], Short> shortArrayType = new ArrayType<>(short[]::new, Short[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Short old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Short[] r = new Short[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            short[] r = new short[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(short.class) : a[i];
            return r;
        });
        ArrayType<int[], Integer[], Integer> integerArrayType = new ArrayType<>(int[]::new, Integer[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Integer old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Integer[] r = new Integer[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            int[] r = new int[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(int.class) : a[i];
            return r;
        });
        ArrayType<long[], Long[], Long> longArrayType = new ArrayType<>(long[]::new, Long[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Long old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Long[] r = new Long[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            long[] r = new long[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(long.class) : a[i];
            return r;
        });
        ArrayType<float[], Float[], Float> floatArrayType = new ArrayType<>(float[]::new, Float[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Float old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Float[] r = new Float[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            float[] r = new float[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(float.class) : a[i];
            return r;
        });
        ArrayType<double[], Double[], Double> doubleArrayType = new ArrayType<>(double[]::new, Double[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> {
            Double old = a[i];
            a[i] = e;
            return old;
        }, a -> {
            Double[] r = new Double[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i];
            return r;
        }, a -> {
            double[] r = new double[a.length];
            for (int i = 0; i < a.length; i++) r[i] = a[i] == null ? Arg.baseTypeDefaultValue(double.class) : a[i];
            return r;
        });
        Map<Class<?>, ArrayType<?, ?, ?>> arrayTypeInfo = new HashMap<>(16, 1);
        arrayTypeInfo.put(CLASSES_Z, booleanArrayType);
        arrayTypeInfo.put(CLASSES_WZ, booleanArrayType);
        arrayTypeInfo.put(CLASSES_C, characterArrayType);
        arrayTypeInfo.put(CLASSES_WC, characterArrayType);
        arrayTypeInfo.put(CLASSES_B, byteArrayType);
        arrayTypeInfo.put(CLASSES_WB, byteArrayType);
        arrayTypeInfo.put(CLASSES_S, shortArrayType);
        arrayTypeInfo.put(CLASSES_WS, shortArrayType);
        arrayTypeInfo.put(CLASSES_I, integerArrayType);
        arrayTypeInfo.put(CLASSES_WI, integerArrayType);
        arrayTypeInfo.put(CLASSES_J, longArrayType);
        arrayTypeInfo.put(CLASSES_WJ, longArrayType);
        arrayTypeInfo.put(CLASSES_F, floatArrayType);
        arrayTypeInfo.put(CLASSES_WF, floatArrayType);
        arrayTypeInfo.put(CLASSES_D, doubleArrayType);
        arrayTypeInfo.put(CLASSES_WD, doubleArrayType);
        arrayTypeInfo.put(CLASSES_OBJECT, new ArrayType<>(Object[]::new, Object[]::new, a -> a.length, (a, i) -> a[i], (a, i, e) -> a[i] = e, a -> a, a -> a));
        ARRAY_TYPE_INFO = Collections.unmodifiableMap(arrayTypeInfo);
    }

    private static class BasicType {
        private final int level;
        private final Class<?> toType;
        private final Object defaultValue;
        private final BiFunction<String, String, Object> converter;

        private BasicType(int level, Class<?> toType, Object defaultValue, Function<String, Object> converter) {
            this.level = level;
            this.toType = toType;
            this.defaultValue = defaultValue;
            this.converter = converter == null ? null : (value, main) -> {
                try {
                    return converter.apply(value);
                } catch (Throwable e) {
                    throw new IllegalArgumentException(String.format("The '%s' value was set to '%s', must be an %s", main, value, BASIC_TYPE_INFO.get(toType).toType.getTypeName()), e);
                }
            };
        }
    }

    private static class ArrayType<P, W, E> {
        private final IntFunction<P> pn;
        private final IntFunction<W> wn;
        private final ArrayLengthGetter<P> length;
        private final ArrayElementGetter<P, E> getter;
        private final ArrayElementSetter<P, E> setter;
        private final Function<P, W> pc;
        private final Function<W, P> wc;

        private ArrayType(IntFunction<P> pn, IntFunction<W> wn,
                          ArrayLengthGetter<P> length,
                          ArrayElementGetter<P, E> getter,
                          ArrayElementSetter<P, E> setter, Function<P, W> pc, Function<W, P> wc) {
            this.pn = pn;
            this.wn = wn;
            this.length = length;
            this.getter = getter;
            this.setter = setter;
            this.pc = pc;
            this.wc = wc;
        }
    }

    public static <A> A array(Class<A> array, int size) {
        if (!array.isArray()) {
            throw new IllegalArgumentException(array.getName() + " is not an array class");
        }
        if (size < 0) size = 0;
        Object arrayType = ARRAY_TYPE_INFO.get(array);
        if (arrayType != null) {
            if (array.getComponentType().isPrimitive()) {
                //noinspection unchecked
                return ((ArrayType<A, Object, Object>) arrayType).pn.apply(size);
            } else {
                //noinspection unchecked
                return ((ArrayType<Object, A, Object>) arrayType).wn.apply(size);
            }
        } else {
            //noinspection unchecked
            return (A) Array.newInstance(array, size);
        }
    }

    public static <A> int arrayLength(A array) {
        if (array == null) return 0;
        Class<?> k = array.getClass();
        if (!k.isArray()) {
            throw new IllegalArgumentException(k.getName() + " is not an array class");
        }
        Object arrayType = ARRAY_TYPE_INFO.get(k);
        if (arrayType == null) {
            arrayType = ARRAY_TYPE_INFO.get(CLASSES_OBJECT);
        }
        //noinspection unchecked
        return ((ArrayType<Object, Object, Object>) arrayType).length.apply(array);
    }

    public static <A, E> E arrayElement(A array, int index) {
        if (array == null) return null;
        Class<?> k = array.getClass();
        if (!k.isArray()) {
            throw new IllegalArgumentException(k.getName() + " is not an array class");
        }
        Object arrayType = ARRAY_TYPE_INFO.get(k);
        if (arrayType == null) {
            arrayType = ARRAY_TYPE_INFO.get(CLASSES_OBJECT);
        }
        //noinspection unchecked
        return ((ArrayType<A, Object, E>) arrayType).getter.apply(array, index);
    }

    public static <A, E> E arrayElement(A array, int index, E element) {
        if (array == null) return null;
        Class<?> k = array.getClass();
        if (!k.isArray()) {
            throw new IllegalArgumentException(k.getName() + " is not an array class");
        }
        Object arrayType = ARRAY_TYPE_INFO.get(k);
        if (arrayType == null) arrayType = ARRAY_TYPE_INFO.get(CLASSES_OBJECT);
        //noinspection unchecked
        return ((ArrayType<A, Object, E>) arrayType).setter.apply(array, index, element);
    }

    public static <A, R> R arrayFlip(A array) {
        Class<?> k = array.getClass();
        if (!k.isArray()) {
            throw new IllegalArgumentException(k.getName() + " is not an array class");
        }
        Object arrayType = ARRAY_TYPE_INFO.get(k);
        if (arrayType == null) arrayType = ARRAY_TYPE_INFO.get(CLASSES_OBJECT);
        if (k.getComponentType().isPrimitive()) {
            //noinspection unchecked
            return ((ArrayType<A, R, Object>) arrayType).pc.apply(array);
        } else {
            //noinspection unchecked
            return ((ArrayType<R, A, Object>) arrayType).wc.apply(array);
        }
    }

    /**
     * 基本类型的包装类型与原生类型互相转换
     *
     * @param input 输入类型
     * @param wrap  是否转为包装类型
     * @return 返回指定类型，如果不支持则返回{@code input}
     */
    public static Class<?> baseTypeConversion(Class<?> input, boolean wrap) {
        Class<?> r = null;
        if (input.isPrimitive()) {
            if (wrap) r = BASIC_TYPE_INFO.getOrDefault(input, NULL_BASIC_TYPE).toType;
        } else {
            if (!wrap) {
                r = BASIC_TYPE_INFO.getOrDefault(input, NULL_BASIC_TYPE).toType;
                if (r == null && Log.isEnabledDebug()) {
                    Log.debug("Not a known primitive wrapper class: " + input);
                }
            }
        }
        if (r == null) r = input;
        return r;
    }

    /**
     * 基本类型的默认值
     *
     * @param input 输入类型
     * @return 如果是基本类型则返回默认值，否则返回null
     */
    public static <T> T baseTypeDefaultValue(Class<T> input) {
        //noinspection unchecked
        return (T) BASIC_TYPE_INFO.getOrDefault(input, NULL_BASIC_TYPE).defaultValue;
    }

    public static boolean isNoNull(Object input) {
        return input != null;
    }

    public static boolean isNoNull(Object input, Object other) {
        return input != null && other != null;
    }

    public static boolean isNoEmpty(CharSequence input) {
        return input != null && input.length() != 0;
    }

    public static boolean isNoEmpty(Collection<?> input) {
        return input != null && !input.isEmpty();
    }

    public static boolean isNoEmpty(Object[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(boolean[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(char[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(byte[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(short[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(int[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(long[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(float[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isNoEmpty(double[] input) {
        return input != null && input.length != 0;
    }

    public static boolean isElementsNoNull(Collection<?> input) {
        if (!Arg.isNoEmpty(input)) return false;
        for (Object e : input) if (!Arg.isNoNull(e)) return false;
        return true;
    }

    public static boolean isElementsNoNull(Object[] input) {
        if (!Arg.isNoEmpty(input)) return false;
        for (Object e : input) if (!Arg.isNoNull(e)) return false;
        return true;
    }

    public static boolean isElementsNoEmpty(Collection<? extends CharSequence> input) {
        if (!Arg.isNoEmpty(input)) return false;
        for (CharSequence e : input) if (!Arg.isNoEmpty(e)) return false;
        return true;
    }

    public static boolean isElementsNoEmpty(CharSequence[] input) {
        if (!Arg.isNoEmpty(input)) return false;
        for (CharSequence e : input) if (!Arg.isNoEmpty(e)) return false;
        return true;
    }

    public static boolean isPeriphery(CharSequence input, CharSequence other) {
        return input != null && other != null && other.length() <= input.length() && other.length() != 0;
    }

    public static boolean isContain(CharSequence input, CharSequence other) {
        if (!Arg.isPeriphery(input, other)) return false;
        int inputLen = input.length(), subLen = other.length();
        int len = inputLen - subLen;
        for (int i = 0; i < len; i++) {
            if (Arg.isMatchRegion(false, input, i, other, 0, subLen)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEquals(CharSequence input, CharSequence other) {
        if (input == other) {
            return true;
        }
        if (input == null || other == null) {
            return false;
        }
        if (input instanceof String && other instanceof String) {
            return input.equals(other);
        }
        return input.length() == other.length() && Arg.isMatchRegion(false, input, 0, other, 0, input.length());
    }

    /**
     * 测试两个字符串区域是否相等。
     *
     * @param ignoreCase  是否忽略大小写
     * @param input       输入字符串
     * @param inputOffset 输入字符串偏移量
     * @param other       匹配字符串
     * @param otherOffset 匹配字符串偏移
     * @param length      要比对字符串长度
     * @return 如果此字符串的指定子区域与字符串参数的指定子区域完全匹配返回true，否则返回false
     * @see String#regionMatches(boolean, int, String, int, int)
     */
    public static boolean isMatchRegion(boolean ignoreCase, CharSequence input, int inputOffset, CharSequence other, int otherOffset, int length) {
        if (input instanceof String && other instanceof String) {
            return ((String) input).regionMatches(ignoreCase, inputOffset, (String) other, otherOffset, length);
        }
        int i1 = inputOffset, i2 = otherOffset;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            char c1 = input.charAt(i1++), c2 = other.charAt(i2++);
            if (c1 == c2) continue;
            if (!ignoreCase) return false;

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMatchCodepoint(CharSequence input, IntPredicate charMatch) {
        int[] array = input.codePoints().toArray();
        for (int c : array) {
            if (!charMatch.test(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否与指定的通配符匹配器匹配，
     * <p>
     * 通配符匹配器使用字符 '?' 和 '*' 来表示单个或多个（零个或多个）通配符。
     *
     * @param input        输入字符串
     * @param patternMatch 匹配通配符模式
     * @param ignoreCase   是否区分大小写
     * @return 如果匹配返回true，否则返回false
     */
    public static boolean isMatchWildcard(CharSequence input, CharSequence patternMatch, boolean ignoreCase) {
        if (input == null || patternMatch == null) return false;
        int inputLength = input.length(), patternLength = patternMatch.length();
        if (inputLength == 0 || patternLength == 0) return false;
        StringBuilder patternBuffer = new StringBuilder();
        int questionCount = 0, multiplyCount = 0;
        boolean preMultiply = false, hasNormal = false;
        char pc, ic;
        for (int i = 0; i < patternLength; i++) {
            pc = patternMatch.charAt(i);
            if (pc != '*') {
                patternBuffer.append(pc);
                preMultiply = false;
                if (pc != '?') hasNormal = true;
                else questionCount++;
            } else if (!preMultiply) {
                patternBuffer.append(pc);
                preMultiply = true;
                multiplyCount++;
            }
        }
        if (!hasNormal) {
            return questionCount == 0 || (multiplyCount > 0 ? inputLength >= questionCount : inputLength == questionCount);
        }
        patternLength = patternBuffer.length();
        if (questionCount == 0 && multiplyCount == 0) {
            if (inputLength != patternLength) return false;
            for (int i = 0; i < inputLength; i++) {
                if (!Arg.isEqualChar(input.charAt(i), patternBuffer.charAt(i), ignoreCase)) return false;
            }
            return true;
        } else if (multiplyCount == 0) {
            if (inputLength != patternLength) return false;
            for (int i = 0; i < inputLength; i++) {
                if ((pc = patternBuffer.charAt(i)) != '?' && !Arg.isEqualChar(input.charAt(i), pc, ignoreCase))
                    return false;
            }
            return true;
        } else {
            int si = 0, pi = 0, i;
            String[] ps = new String[multiplyCount + 1];
            if (patternBuffer.charAt(0) == '*') {
                ps[pi++] = "";
                si = 1;
            }
            for (i = pi; i < patternLength; i++) {
                if (patternBuffer.charAt(i) != '*') continue;
                ps[pi++] = patternBuffer.substring(si, i);
                si = i + 1;
            }
            if (si >= patternLength) ps[pi] = "";
            else ps[pi] = patternBuffer.substring(si);
            int[] pcs = new int[ps.length];
            pcs[pcs.length - 1] = ps[pcs.length - 1].length();
            for (i = pcs.length - 2; i >= 0; i--) pcs[i] = pcs[i + 1] + ps[i].length();
            if (inputLength < pcs[0]) return false;
            final int s = ps[0].isEmpty() ? 1 : 0, e = ps.length - (ps[ps.length - 1].isEmpty() ? 1 : 0);
            for (si = 0, i = s; i < e; ) {
                int ii = 0;
                for (pi = 0; pi < ps[i].length(); pi++) {
                    if (si + ii >= inputLength) return false;//模式未完全匹配，已到字符串末尾
                    ic = input.charAt(si + ii);
                    pc = ps[i].charAt(pi);
                    if (pc != '?' && !Arg.isEqualChar(ic, pc, ignoreCase)) break;
                    ii++;
                }
                if (pi < ps[i].length()) {//模式匹配失败
                    if (inputLength - si - 1 < pcs[i]) return false;//剩余字符串长度，不满足模式匹配长度
                    si++;
                } else {
                    si += ii;
                    i++;
                }
            }
            return e != ps.length || si == inputLength;
        }
    }

    public static boolean isNoNegative(long input) {
        return input >= 0;
    }

    public static boolean isNatural(long input, long limit) {
        return input >= 0 && input < limit;
    }

    public static boolean isRange(long input, long lo, long hi) {
        return input >= lo && input < hi;
    }

    public static boolean isExcludeRange(long input, long lo, long hi) {
        return !isRange(input, lo, hi);
    }

    public static boolean isBlankChar(int input) {
        return Character.isWhitespace(input) || Character.isSpaceChar(input) || input == '\ufeff' || input == '\u202a';
    }

    public static boolean isAsciiChar(int input) {
        return input < 128;
    }

    public static boolean isAsciiPrintableChar(int input) {
        return input >= 32 && input < 127;
    }

    public static boolean isAsciiControlChar(int input) {
        return input < 32 || input == 127;
    }

    public static boolean isLetterChar(int input, Boolean upper) {
        boolean r = upper == null || upper;
        if (r) r = input >= 'A' && input <= 'Z';
        if (!r && (upper == null || !upper)) {
            r = input >= 'a' && input <= 'z';
        }
        return r;
    }

    public static boolean isNumberChar(int input, boolean hex) {
        return input >= '0' && input <= '9' || (hex && ((input >= 'a' && input <= 'f') || (input >= 'A' && input <= 'F')));
    }

    public static boolean isLetterOrNumberChar(int input) {
        return Arg.isLetterChar(input, null) || Arg.isNumberChar(input, false);
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1         字符1
     * @param c2         字符2
     * @param ignoreCase 是否忽略大小写
     * @return 是否相同
     */
    public static boolean isEqualChar(char c1, char c2, boolean ignoreCase) {
        return c1 == c2 || (ignoreCase && Character.toLowerCase(c1) == Character.toLowerCase(c2));
    }

    /**
     * 比较字符数组和字符串是否相等
     *
     * @param chars        字符数组
     * @param csi          字符起始位置
     * @param cei          字符结束位置
     * @param isIgnoreCase 忽略字符串
     * @return 返回是否相等
     */
    public static boolean isEqualChars(char[] chars, int csi, int cei, char[] other, boolean isIgnoreCase) {
        return Arg.isEqualChars(chars, csi, cei, other, 0, other.length, isIgnoreCase);
    }

    /**
     * 比较字符数组和字符串是否相等
     *
     * @param chars        字符数组
     * @param csi          字符起始位置
     * @param cei          字符结束位置
     * @param other        字符串
     * @param osi          字符串起始位置
     * @param oei          字符串结束位置
     * @param isIgnoreCase 忽略字符串
     * @return 返回是否相等
     */
    public static boolean isEqualChars(char[] chars, int csi, int cei, char[] other, int osi, int oei, boolean isIgnoreCase) {
        if (oei - osi != cei - csi) return false;
        int len = oei - osi;
        for (int j = 0; j < len; j++) {
            if (!Arg.isEqualChar(chars[csi + j], other[osi + j], isIgnoreCase)) return false;
        }
        return true;
    }

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param input 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isPathSeparatorChar(int input) {
        return '/' == input || '\\' == input;
    }

    /**
     * 是否有效类名
     *
     * @param className 类名
     * @return 如果有效返回true，否则返回false
     */
    public static boolean isAvailableClassName(String className) {
        try {
            Class.forName(className, false, ClassLoader.getSystemClassLoader());
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isWrapperType(Class<?> clazz) {
        return null != clazz && !clazz.isPrimitive() && BASIC_TYPE_INFO.containsKey(clazz);
    }

    /**
     * 判断 {@code targetType}是{@code inputType}或接口，
     *
     * @param inputType  要判断的类型，不允许为null
     * @param targetType 是否兼容父级类型，不允许为null
     * @return 如果兼容返回true，否则返回false
     */
    public static boolean isImplementsFrom(Class<?> inputType, Class<?> targetType) {
        return inputType != targetType && targetType.isAssignableFrom(inputType);
    }

    /**
     * 判断{@code targetType}是否兼容{@code inputType}，
     * {@code targetType}是{@code inputType}或接口，
     * 或{@code targetType}是{@code inputType}或更大的基本类型
     *
     * @param inputType  要判断的类型，允许为null
     * @param targetType 是否兼容父级类型，不允许为null
     * @return 如果兼容返回true，否则返回false
     */
    public static boolean isCompatibilityFrom(Class<?> inputType, Class<?> targetType) {
        if (targetType == null) throw new IllegalArgumentException("The match class must no null");
        if (inputType == targetType || inputType == null) return true;
        boolean inputArray = inputType.isArray();
        boolean matchArray = targetType.isArray();
        if (inputArray && matchArray) {
            return targetType.isAssignableFrom(inputType);
        } else if (inputArray || matchArray) {
            return false;
        } else {
            boolean matchPrimitive = targetType.isPrimitive();
            boolean inputPrimitive = inputType.isPrimitive();
            if (matchPrimitive) {
                int matchCompatibility = BASIC_TYPE_INFO.get(targetType).level;
                int inputCompatibility = BASIC_TYPE_INFO.getOrDefault(inputType, NULL_BASIC_TYPE).level;
                if (inputCompatibility < 0) return false;
                if (inputCompatibility > 0 && matchCompatibility > 0) {
                    return matchCompatibility >= inputCompatibility;//匹配类型是原生类型的兼容性
                } else if (inputCompatibility == 0 && matchCompatibility == 0) {
                    if (inputPrimitive) return targetType == inputType;
                    else if (inputType == CLASS_WZ && targetType == CLASS_Z) return true;
                    else return inputType == CLASS_WC && targetType == CLASS_C;
                }
                return false;
            } else if (inputPrimitive) {
                return targetType.isAssignableFrom(BASIC_TYPE_INFO.get(inputType).toType);
            } else {
                return targetType.isAssignableFrom(inputType);
            }
        }
    }

    /**
     * 比较判断{@code inputType}是否兼容{@code targetType}，
     * {@code targetType}是{@code inputType}或接口，
     * 或{@code targetType}是{@code inputType}或更大的基本类型
     *
     * @param inputCLasses 要判断的类型，允许内部元素为null
     * @param matchClasses 是否兼容父级类型，不允许内部元素为null
     * @return 如果兼容返回true，否则返回false
     */
    public static boolean isCompatibilityFrom(Class<?>[] inputCLasses, Class<?>[] matchClasses) {
        if (matchClasses == inputCLasses) return true;
        if (null == matchClasses || null == inputCLasses) return false;
        if (matchClasses.length != inputCLasses.length) return false;
        for (int i = 0; i < matchClasses.length; i++) {
            if (!Arg.isCompatibilityFrom(inputCLasses[i], matchClasses[i])) {
                return false;
            }
        }
        return true;
    }

    public static void checkState(boolean success) {
        Arg.checkAndReturn(success, null);
    }

    public static void checkState(boolean success, String messageTemplate, Object... args) {
        Arg.checkAndReturn(success, null, messageTemplate, args);
    }

    public static void checkState(boolean success, Supplier<String> messageSupplier) {
        Arg.checkAndReturn(success, null, messageSupplier);
    }

    public static <T> T checkStateAndReturn(boolean success, T input) {
        if (!success) {
            throw new IllegalStateException();
        }
        return input;
    }

    public static <T> T checkStateAndReturn(boolean success, T input, String messageTemplate, Object... args) {
        if (!success) {
            throw new IllegalStateException(String.format(messageTemplate, args));
        }
        return input;
    }

    public static <T> T checkStateAndReturn(boolean success, T input, Supplier<String> messageSupplier) {
        if (!success) {
            throw new IllegalStateException(messageSupplier.get());
        }
        return input;
    }

    public static void check(boolean success) {
        Arg.checkAndReturn(success, null);
    }

    public static void check(boolean success, String messageTemplate, Object... args) {
        Arg.checkAndReturn(success, null, messageTemplate, args);
    }

    public static void check(boolean success, Supplier<String> messageSupplier) {
        Arg.checkAndReturn(success, null, messageSupplier);
    }

    public static <T> T checkAndReturn(boolean success, T input) {
        if (!success) {
            throw new IllegalArgumentException();
        }
        return input;
    }

    public static <T> T checkAndReturn(boolean success, T input, String messageTemplate, Object... args) {
        if (!success) {
            throw new IllegalArgumentException(String.format(messageTemplate, args));
        }
        return input;
    }

    public static <T> T checkAndReturn(boolean success, T input, Supplier<String> messageSupplier) {
        if (!success) {
            throw new IllegalArgumentException(messageSupplier.get());
        }
        return input;
    }

    public static <T> T checkNoNull(T input) {
        return Arg.checkAndReturn(Arg.isNoNull(input), input, "The input value must be not null");
    }

    public static <T> T checkNoNull(T input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoNull(input), input, messageTemplate, args);
    }

    public static <T> T checkNoNull(T input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoNull(input), input, messageSupplier);
    }

    public static void checkNoNull(Object input, Object other) {
        Arg.check(Arg.isNoNull(input, other), null, "The input value must be not null");
    }

    public static void checkNoNull(Object input, Object other, String messageTemplate, Object... args) {
        Arg.checkAndReturn(Arg.isNoNull(input, other), null, messageTemplate, args);
    }

    public static void checkNoNull(Object input, Object other, Supplier<String> messageSupplier) {
        Arg.checkAndReturn(Arg.isNoNull(input, other), null, messageSupplier);
    }

    public static <T extends CharSequence> T checkNoEmpty(T input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static <T extends CharSequence> T checkNoEmpty(T input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static <T extends CharSequence> T checkNoEmpty(T input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static <E, T extends Collection<E>> T checkNoEmpty(T input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static <E, T extends Collection<E>> T checkNoEmpty(T input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static <E, T extends Collection<E>> T checkNoEmpty(T input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static <T> T[] checkNoEmpty(T[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static <T> T[] checkNoEmpty(T[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static <T> T[] checkNoEmpty(T[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static boolean[] checkNoEmpty(boolean[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static boolean[] checkNoEmpty(boolean[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static boolean[] checkNoEmpty(boolean[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static char[] checkNoEmpty(char[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static char[] checkNoEmpty(char[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static char[] checkNoEmpty(char[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static byte[] checkNoEmpty(byte[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static byte[] checkNoEmpty(byte[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static byte[] checkNoEmpty(byte[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static short[] checkNoEmpty(short[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static short[] checkNoEmpty(short[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static short[] checkNoEmpty(short[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static int[] checkNoEmpty(int[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static int[] checkNoEmpty(int[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static int[] checkNoEmpty(int[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static long[] checkNoEmpty(long[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static long[] checkNoEmpty(long[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static long[] checkNoEmpty(long[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static float[] checkNoEmpty(float[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static float[] checkNoEmpty(float[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static float[] checkNoEmpty(float[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static double[] checkNoEmpty(double[] input) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static double[] checkNoEmpty(double[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static double[] checkNoEmpty(double[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static <E, T extends Collection<E>> T checkElementsNoNull(T input) {
        return Arg.checkAndReturn(Arg.isElementsNoNull(input), input, "The input collection value must be not null");
    }

    public static <E, T extends Collection<E>> T checkElementsNoNull(T input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isElementsNoNull(input), input, messageTemplate, args);
    }

    public static <E, T extends Collection<E>> T checkElementsNoNull(T input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isElementsNoNull(input), input, messageSupplier);
    }

    public static <E> E[] checkElementsNoNull(E[] input) {
        return Arg.checkAndReturn(Arg.isElementsNoNull(input), input, "The input array value must be not null");
    }

    public static <E> E[] checkElementsNoNull(E[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isElementsNoNull(input), input, messageTemplate, args);
    }

    public static <E> E[] checkElementsNoNull(E[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isElementsNoNull(input), input, messageSupplier);
    }

    public static <T extends Collection<? extends CharSequence>> T checkElementsNoEmpty(T input) {
        return Arg.checkAndReturn(Arg.isElementsNoEmpty(input), input, "The input array value must be not empty");
    }

    public static <T extends Collection<? extends CharSequence>> T checkElementsNoEmpty(T input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isElementsNoEmpty(input), input, messageTemplate, args);
    }

    public static <T extends Collection<? extends CharSequence>> T checkElementsNoEmpty(T input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isElementsNoEmpty(input), input, messageSupplier);
    }

    public static <E extends CharSequence> E[] checkElementsNoEmpty(E[] input) {
        return Arg.checkAndReturn(Arg.isElementsNoEmpty(input), input, "The input array value must be not empty");
    }

    public static <E extends CharSequence> E[] checkElementsNoEmpty(E[] input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isElementsNoEmpty(input), input, messageTemplate, args);
    }

    public static <E extends CharSequence> E[] checkElementsNoEmpty(E[] input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isElementsNoEmpty(input), input, messageSupplier);
    }

    public static void checkContain(String input, String ele) {
        Arg.check(Arg.isContain(input, ele), "The substring '%s' is not in the string '%s'", ele, input);
    }

    public static void checkContain(String input, String ele, String messageTemplate, Object... args) {
        Arg.checkAndReturn(Arg.isContain(input, ele), null, messageTemplate, args);
    }

    public static void checkContain(String input, String ele, Supplier<String> messageSupplier) {
        Arg.checkAndReturn(Arg.isContain(input, ele), null, messageSupplier);
    }

    public static <T extends CharSequence> T checkMatch(T input, IntPredicate charMatch) {
        return Arg.checkMatch(input, charMatch, value -> "The '" + input + "' string contains invalid characters '" + ((char) value) + "'");
    }

    public static <T extends CharSequence> T checkMatch(T input, IntPredicate charMatch, IntFunction<String> messageSupplier) {
        int[] array = input.codePoints().toArray();
        for (int c : array) {
            if (!charMatch.test(c)) {
                throw new IllegalArgumentException(messageSupplier.apply(c));
            }
        }
        return input;
    }

    public static void checkElementIndex(int index, int size) {
        Arg.check(index >= 0 && index < size, "The index must be 0<=index(%d)x<size(%d)", index, size);
    }

    public static void checkElementIndex(int start, int end, int size) {
        Arg.check(start <= end && start >= 0 && end < size, "The index must be 0<=start(%d)<=end(%d)<size(%d)", start, end, size);

    }

    public static Duration checkNoNegative(Duration input) {
        return Arg.checkAndReturn(input != null && !input.isNegative(), input, "The value '%s', but the duration cannot be negative", input);
    }

    public static long checkNoNegative(long input) {
        return Arg.checkAndReturn(input >= 0, input, "The value '%s', but it must be no negative", input);
    }

    public static long checkNoNegative(long input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static long checkNoNegative(long input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static int checkNoNegative(int input) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, "The value '%s', but it must be no negative", input);
    }

    public static int checkNoNegative(int input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static int checkNoNegative(int input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static short checkNoNegative(short input) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, "The value '%s', but it must be no negative", input);
    }

    public static short checkNoNegative(short input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static short checkNoNegative(short input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static byte checkNoNegative(byte input) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, "The value '%s', but it must be no negative", input);
    }

    public static byte checkNoNegative(byte input, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static byte checkNoNegative(byte input, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static long checkNatural(long input, long limit) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static long checkNatural(long input, long limit, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static long checkNatural(long input, long limit, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static int checkNatural(int input, int limit) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static int checkNatural(int input, int limit, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static int checkNatural(int input, int limit, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static short checkNatural(short input, short limit) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static short checkNatural(short input, short limit, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static short checkNatural(short input, short limit, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static byte checkNatural(byte input, byte limit) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static byte checkNatural(byte input, byte limit, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static byte checkNatural(byte input, byte limit, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static long checkRange(long input, long lo, long hi) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static long checkRange(long input, long lo, long hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static long checkRange(long input, long lo, long hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static int checkRange(int input, int lo, int hi) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static int checkRange(int input, int lo, int hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static int checkRange(int input, int lo, int hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static short checkRange(short input, short lo, short hi) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static short checkRange(short input, short lo, short hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static short checkRange(short input, short lo, short hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static byte checkRange(byte input, byte lo, byte hi) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static byte checkRange(byte input, byte lo, byte hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static byte checkRange(byte input, byte lo, byte hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static long checkExcludeRange(long input, long lo, long hi) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static long checkExcludeRange(long input, long lo, long hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static long checkExcludeRange(long input, long lo, long hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static int checkExcludeRange(int input, int lo, int hi) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static int checkExcludeRange(int input, int lo, int hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static int checkExcludeRange(int input, int lo, int hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static short checkExcludeRange(short input, short lo, short hi) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static short checkExcludeRange(short input, short lo, short hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static short checkExcludeRange(short input, short lo, short hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static byte checkExcludeRange(byte input, byte lo, byte hi) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static byte checkExcludeRange(byte input, byte lo, byte hi, String messageTemplate, Object... args) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static byte checkExcludeRange(byte input, byte lo, byte hi, Supplier<String> messageSupplier) {
        return Arg.checkAndReturn(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static void checkCompatibilityFrom(Class<?> inputClass, Class<?> matchClass) {
        Arg.check(Arg.isCompatibilityFrom(inputClass, matchClass), null, "This match type '%s' is not compatible with input type '%s'", matchClass, inputClass);
    }

    public static void checkCompatibilityFrom(Class<?> inputClass, Class<?> matchClass, String messageTemplate, Object... args) {
        Arg.checkAndReturn(Arg.isCompatibilityFrom(inputClass, matchClass), null, messageTemplate, args);
    }

    public static void checkCompatibilityFrom(Class<?> inputClass, Class<?> matchClass, Supplier<String> messageSupplier) {
        Arg.checkAndReturn(Arg.isCompatibilityFrom(inputClass, matchClass), null, messageSupplier);
    }

    public static Charset effectiveCharset(Object input) {
        if (input instanceof CharSequence) {
            try {
                return Charset.forName(input.toString());
            } catch (Throwable e) {
                throw new IllegalArgumentException("Error to parse charset", e);
            }
        } else if (input instanceof Charset) return (Charset) input;
        else if (input == null) return Charset.defaultCharset();
        throw new IllegalArgumentException("The input parameters do not meet the requirements");
    }

    public static Locale effectiveLocale(Object input) {
        if (input instanceof CharSequence) {
            /*
             * 将给定的{@code String}表示形式解析为{@link Locale}，格式如下：
             * <ul>
             *   <li>en
             *   <li>en_US
             *   <li>zh CN
             * </ul>
             */
            String localeName = Arg.checkMatch((CharSequence) input, v -> v == '_' || v == ' ' || Character.isLetterOrDigit(v)).toString();
            if (!Arg.isNoEmpty(localeName)) return Locale.getDefault();
            try {
                StringTokenizer st = new StringTokenizer(localeName, "_ ");
                String first = st.nextToken();
                String second = st.hasMoreTokens() ? st.nextToken() : null;
                if (second == null) {
                    Locale resolved = Locale.forLanguageTag(first);
                    if (resolved != null) {
                        String language = resolved.getLanguage();
                        if (language != null && !language.isEmpty()) return resolved;
                    }
                }
                String country = second == null ? "" : second;
                String variant = "";
                if (st.hasMoreTokens()) {
                    // There is definitely a variant, and it is everything after the country
                    // code sans the separator between the country code and the variant.
                    int endIndexOfCountryCode = localeName.indexOf(country) + country.length();
                    // Strip off any leading '_' and whitespace, what's left is the variant.
                    variant = localeName.substring(endIndexOfCountryCode).trim();
                    int index = 0;
                    for (int i = 0; i < variant.length(); i++) {
                        if (variant.charAt(i) == '_') index++;
                        else break;
                    }
                    variant = variant.substring(index);
                }
                if (variant.isEmpty() && country.startsWith("#")) {
                    variant = country;
                    country = "";
                }
                return new Locale(first, country, variant);
            } catch (Throwable e) {
                throw new IllegalArgumentException("Error to parse locale", e);
            }
        } else if (input instanceof Locale) return (Locale) input;
        else if (input == null) return Locale.getDefault();
        throw new IllegalArgumentException("The input parameters do not meet the requirements");
    }

    public static ZoneId effectiveZoneId(Object input) {
        try {
            if (input instanceof ZoneId) return (ZoneId) input;
            else if (input instanceof CharSequence) return ZoneId.of(input.toString());
            else if (input == null) return ZoneId.systemDefault();
            else if (input instanceof TemporalAccessor) return ZoneId.from((TemporalAccessor) input);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Error to parse zone", e);
        }
        throw new IllegalArgumentException("The input parameters do not meet the requirements");
    }

    public static File effectiveFile(Object input) {
        if (input == null) return null;
        else if (input instanceof File) return (File) input;
        else if (input instanceof Path) return ((Path) input).toFile();
        else if (input instanceof CharSequence) return new File(input.toString());
        throw new IllegalArgumentException("The input parameters do not meet the requirements");
    }

    public static Path effectivePath(Object input) {
        if (input == null) return null;
        else if (input instanceof Path) return (Path) input;
        else if (input instanceof File) return ((File) input).toPath();
        else if (input instanceof CharSequence) return Paths.get(input.toString());
        throw new IllegalArgumentException("The input parameters do not meet the requirements");
    }

    /**
     * Returns a parsed duration value.
     */
    public static Duration parseDuration(String value, Duration defaultValue) {
        return Arg.parseDuration(value, defaultValue, "Input Duration");
    }

    /**
     * Returns a parsed duration value.
     */
    public static Duration parseDuration(String value, Duration defaultValue, String main) {
        if (value == null) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("The value of '" + main + "' must be not null");
            } else {
                return defaultValue;
            }
        }
        int length = value.length();
        if (length == 0) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("The value of '" + main + "' must be not empty");
            } else {
                return defaultValue;
            }
        }
        boolean isIsoFormat = value.contains("p") || value.contains("P");
        if (isIsoFormat) {
            Duration duration = Duration.parse(value);
            if (duration.isNegative()) {
                throw new IllegalArgumentException(String.format(
                        "main %s invalid format; was %s, but the duration cannot be negative", main, value));
            }
            return duration;
        }
        TimeUnit timeUnit = Arg.parseTimeUnit(value, null, main);

        int i = value.length() - 2;
        if (Arg.isNumberChar(value.charAt(i), false)) {
            i++;
        }

        long duration = Arg.parseLong(value.substring(0, i), null, main);
        long nanos = timeUnit.toNanos(duration);
        return Duration.ofNanos(nanos);
    }

    /**
     * Returns a parsed {@link TimeUnit} value.
     */
    public static TimeUnit parseTimeUnit(String value, TimeUnit defaultValue) {
        return Arg.parseTimeUnit(value, defaultValue, "Input TimeUnit");
    }

    /**
     * Returns a parsed {@link TimeUnit} value.
     */
    public static TimeUnit parseTimeUnit(String value, TimeUnit defaultValue, String main) {
        if (value == null) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("The value of '" + main + "' must be not null");
            } else {
                return defaultValue;
            }
        }
        int length = value.length();
        if (length == 0) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("The value of '" + main + "' must be not empty");
            } else {
                return defaultValue;
            }
        }
        char c = Character.toLowerCase(value.charAt(length - 1));
        switch (c) {
            case 'd':
                return TimeUnit.DAYS;
            case 'h':
                return TimeUnit.HOURS;
            case 'm':
                return TimeUnit.MINUTES;
            case 's': {
                if (length > 2) {
                    c = Character.toLowerCase(value.charAt(length - 2));
                    if (c == 'm') return TimeUnit.MILLISECONDS;
                }
                return TimeUnit.SECONDS;
            }
            default:
                throw new IllegalArgumentException(String.format(
                        "main %s invalid format; was %s, must end with one of [dDhHmMsS]", main, value));
        }
    }

    public static byte parseByte(String value, Number defaultValue) {
        return Arg.parseNumber(value, byte.class, defaultValue, "Input Number").byteValue();
    }

    public static byte parseByte(String value, Number defaultValue, String main) {
        return Arg.parseNumber(value, byte.class, defaultValue, main).byteValue();
    }

    public static short parseShort(String value, Number defaultValue) {
        return Arg.parseNumber(value, short.class, defaultValue, "Input Number").shortValue();
    }

    public static short parseShort(String value, Number defaultValue, String main) {
        return Arg.parseNumber(value, short.class, defaultValue, main).shortValue();
    }

    public static int parseInt(String value, Number defaultValue) {
        return Arg.parseNumber(value, int.class, defaultValue, "Input Number").intValue();
    }

    public static int parseInt(String value, Number defaultValue, String main) {
        return Arg.parseNumber(value, int.class, defaultValue, main).intValue();
    }

    public static long parseLong(String value, Number defaultValue) {
        return Arg.parseNumber(value, long.class, defaultValue, "Input Number").longValue();
    }

    public static long parseLong(String value, Number defaultValue, String main) {
        return Arg.parseNumber(value, long.class, defaultValue, main).longValue();
    }

    public static float parseFloat(String value, Number defaultValue) {
        return Arg.parseNumber(value, float.class, defaultValue, "Input Number").floatValue();
    }

    public static float parseFloat(String value, Number defaultValue, String main) {
        return Arg.parseNumber(value, float.class, defaultValue, main).floatValue();
    }

    public static double parseDouble(String value, Number defaultValue) {
        return Arg.parseDouble(value, defaultValue, "Input Number");
    }

    public static double parseDouble(String value, Number defaultValue, String main) {
        return Arg.parseNumber(value, double.class, defaultValue, main).doubleValue();
    }

    public static BigInteger parseBigInteger(String value, Number defaultValue) {
        return (BigInteger) Arg.parseNumber(value, BigInteger.class, defaultValue, "Input Number");
    }

    public static BigInteger parseBigInteger(String value, Number defaultValue, String main) {
        return (BigInteger) Arg.parseNumber(value, BigInteger.class, defaultValue, main);
    }

    public static BigDecimal parseBigDecimal(String value, Number defaultValue) {
        return (BigDecimal) Arg.parseNumber(value, BigDecimal.class, defaultValue, "Input Number");
    }

    public static BigDecimal parseBigDecimal(String value, Number defaultValue, String main) {
        return (BigDecimal) Arg.parseNumber(value, BigDecimal.class, defaultValue, main);
    }

    private static Number parseNumber(String value, Class<? extends Number> type, Number defaultValue, String main) {
        if (value == null) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("The value of '" + main + "' must be not null");
            } else {
                return defaultValue;
            }
        }
        BasicType basicType = BASIC_TYPE_INFO.get(type);
        if (basicType != null) {
            try {
                return (Number) basicType.converter.apply(value, main);
            } catch (Throwable e) {
                if (Log.isEnabledDebug()) {
                    Log.debug("Error to parse '" + value + "' to " + type.getSimpleName() + " by basic 'parseXXX' method in Number", e);
                }
            }
        }
        try {
            if (value.lastIndexOf(".") >= 0) {
                BigDecimal result = new BigDecimal(value);
                if (type == BigInteger.class) {
                    return result.toBigIntegerExact();
                }
                return result;
            } else {
                if (type == BigDecimal.class) {
                    return new BigDecimal(value);
                } else {
                    return new BigInteger(value);
                }
            }
        } catch (NumberFormatException e) {
            if (Log.isEnabledDebug()) {
                Log.debug("Error to parse '" + value + "' to " + type.getSimpleName() + " by BigDecimal or BigInteger", e);
            }
        }
        try {
            NumberFormat numberFormat;
            if (value.endsWith("%")) {
                numberFormat = NumberFormat.getPercentInstance();
            } else if (value.indexOf(',') >= 0) {
                numberFormat = NumberFormat.getNumberInstance();
            } else {
                numberFormat = NumberFormat.getCurrencyInstance();
            }
            Number number = numberFormat.parse(value);
            if (type.isInstance(number)) {
                return number;
            }
            if (type == BigDecimal.class) {
                return new BigDecimal(number.toString());
            } else if (type == BigInteger.class) {
                return new BigInteger(number.toString());
            } else {
                return number;
            }
        } catch (ParseException e) {
            if (Log.isEnabledDebug()) {
                Log.debug("Error to parse '" + value + "' to " + type.getSimpleName() + " by NumberFormat", e);
            }
            if (defaultValue == null) {
                throw new IllegalArgumentException("Error to parse '" + value + "' to " + type.getSimpleName());
            } else {
                return defaultValue;
            }
        }
    }
}
