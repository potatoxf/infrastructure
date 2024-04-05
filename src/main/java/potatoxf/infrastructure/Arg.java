package potatoxf.infrastructure;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

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
    public static final Class<?> CLASS_MAP = Map.class, CLASS_LIST = List.class, CLASS_NUMBER = Number.class;
    public static final Class<?> CLASS_Z = boolean.class, CLASS_WZ = Boolean.class, CLASSES_Z = boolean[].class, CLASSES_WZ = Boolean[].class;
    public static final Class<?> CLASS_C = char.class, CLASS_WC = Character.class, CLASSES_C = char[].class, CLASSES_WC = Character[].class;
    public static final Class<?> CLASS_B = byte.class, CLASS_WB = Byte.class, CLASSES_B = byte[].class, CLASSES_WB = Byte[].class;
    public static final Class<?> CLASS_S = short.class, CLASS_WS = Short.class, CLASSES_S = short[].class, CLASSES_WS = Short[].class;
    public static final Class<?> CLASS_I = int.class, CLASS_WI = Integer.class, CLASSES_I = int[].class, CLASSES_WI = Integer[].class;
    public static final Class<?> CLASS_J = long.class, CLASS_WJ = Long.class, CLASSES_J = long[].class, CLASSES_WJ = Long[].class;
    public static final Class<?> CLASS_F = float.class, CLASS_WF = Float.class, CLASSES_F = float[].class, CLASSES_WF = Float[].class;
    public static final Class<?> CLASS_D = double.class, CLASS_WD = Double.class, CLASSES_D = double[].class, CLASSES_WD = Double[].class;
    private static final Map<Class<?>, Integer> BASIC_TYPE_LEVEL;
    private static final Map<Class<?>, Class<?>> BASIC_TYPE_MAPPING;

    static {
        Map<Class<?>, Integer> basicTypeLevel = new HashMap<>(16, 1);
        basicTypeLevel.put(boolean.class, 0);
        basicTypeLevel.put(Boolean.class, 0);
        basicTypeLevel.put(char.class, 0);
        basicTypeLevel.put(Character.class, 0);
        basicTypeLevel.put(byte.class, 1);
        basicTypeLevel.put(Byte.class, 1);
        basicTypeLevel.put(short.class, 2);
        basicTypeLevel.put(Short.class, 2);
        basicTypeLevel.put(int.class, 3);
        basicTypeLevel.put(Integer.class, 3);
        basicTypeLevel.put(long.class, 4);
        basicTypeLevel.put(Long.class, 4);
        basicTypeLevel.put(float.class, 5);
        basicTypeLevel.put(Float.class, 5);
        basicTypeLevel.put(double.class, 6);
        basicTypeLevel.put(Double.class, 6);
        BASIC_TYPE_LEVEL = Collections.unmodifiableMap(basicTypeLevel);
        Map<Class<?>, Class<?>> basicTypeMapping = new HashMap<>(16, 1);
        basicTypeMapping.put(boolean.class, Boolean.class);
        basicTypeMapping.put(Boolean.class, boolean.class);
        basicTypeMapping.put(char.class, char.class);
        basicTypeMapping.put(Character.class, char.class);
        basicTypeMapping.put(byte.class, byte.class);
        basicTypeMapping.put(Byte.class, byte.class);
        basicTypeMapping.put(short.class, short.class);
        basicTypeMapping.put(Short.class, short.class);
        basicTypeMapping.put(int.class, int.class);
        basicTypeMapping.put(Integer.class, int.class);
        basicTypeMapping.put(long.class, long.class);
        basicTypeMapping.put(Long.class, long.class);
        basicTypeMapping.put(float.class, float.class);
        basicTypeMapping.put(Float.class, float.class);
        basicTypeMapping.put(double.class, Double.class);
        basicTypeMapping.put(Double.class, double.class);
        BASIC_TYPE_MAPPING = Collections.unmodifiableMap(basicTypeMapping);
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

    public static boolean isPeriphery(String input, String ele) {
        return input != null && ele != null && ele.length() <= input.length() && !ele.isEmpty();
    }

    public static boolean isContain(String input, String ele) {
        return Arg.isPeriphery(input, ele) && input.contains(ele);
    }

    public static boolean isMatch(String input, IntPredicate charMatch) {
        int[] array = input.codePoints().toArray();
        for (int c : array) {
            if (!charMatch.test(c)) {
                return false;
            }
        }
        return true;
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
        return Character.isWhitespace(input)
                || Character.isSpaceChar(input)
                || input == '\ufeff'
                || input == '\u202a';
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
        return null != clazz && !clazz.isPrimitive() && BASIC_TYPE_MAPPING.containsKey(clazz);
    }

    /**
     * 判断{@code matchClass}是否兼容{@code inputClass}，
     * {@code matchClass}是{@code inputClass}或接口，
     * 或{@code matchClass}是{@code inputClass}或更大的基本类型
     *
     * @param inputClass 要判断的类型，允许为null
     * @param matchClass 是否兼容父级类型，不允许为null
     * @return 如果兼容返回true，否则返回false
     */
    public static boolean isCompatibilityFrom(Class<?> inputClass, Class<?> matchClass) {
        if (matchClass == null) throw new IllegalArgumentException("The match class must no null");
        if (inputClass == matchClass || inputClass == null) return true;
        boolean inputArray = inputClass.isArray();
        boolean matchArray = matchClass.isArray();
        if (inputArray && matchArray) {
            return matchClass.isAssignableFrom(inputClass);
        } else if (inputArray || matchArray) {
            return false;
        } else {
            boolean matchPrimitive = matchClass.isPrimitive();
            boolean inputPrimitive = inputClass.isPrimitive();
            if (matchPrimitive) {
                int matchCompatibility = BASIC_TYPE_LEVEL.get(matchClass);
                int inputCompatibility = BASIC_TYPE_LEVEL.getOrDefault(inputClass, -1);
                if (inputCompatibility < 0) return false;
                if (inputCompatibility > 0 && matchCompatibility > 0) {
                    return matchCompatibility >= inputCompatibility;//匹配类型是原生类型的兼容性
                } else if (inputCompatibility == 0 && matchCompatibility == 0) {
                    if (inputPrimitive) return matchClass == inputClass;
                    else if (inputClass == CLASS_WZ && matchClass == CLASS_Z) return true;
                    else return inputClass == CLASS_WC && matchClass == CLASS_C;
                }
                return false;
            } else if (inputPrimitive) {
                return matchClass.isAssignableFrom(BASIC_TYPE_MAPPING.get(inputClass));
            } else {
                return matchClass.isAssignableFrom(inputClass);
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

    public static <T> T check(boolean success, T input) {
        if (!success) {
            throw new IllegalArgumentException();
        }
        return input;
    }

    public static <T> T check(boolean success, T input, String messageTemplate, Object... args) {
        if (!success) {
            throw new IllegalArgumentException(String.format(messageTemplate, args));
        }
        return input;
    }

    public static <T> T check(boolean success, T input, Supplier<String> messageSupplier) {
        if (!success) {
            throw new IllegalArgumentException(messageSupplier.get());
        }
        return input;
    }

    public static <T> T checkNoNull(T input) {
        return Arg.check(Arg.isNoNull(input), input, "The input value must be not null");
    }

    public static <T> T checkNoNull(T input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoNull(input), input, messageTemplate, args);
    }

    public static <T> T checkNoNull(T input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoNull(input), input, messageSupplier);
    }

    public static void checkNoNull(Object input, Object other) {
        Arg.check(Arg.isNoNull(input, other), null, "The input value must be not null");
    }

    public static void checkNoNull(Object input, Object other, String messageTemplate, Object... args) {
        Arg.check(Arg.isNoNull(input, other), null, messageTemplate, args);
    }

    public static void checkNoNull(Object input, Object other, Supplier<String> messageSupplier) {
        Arg.check(Arg.isNoNull(input, other), null, messageSupplier);
    }

    public static <T extends CharSequence> T checkNoEmpty(T input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static <T extends CharSequence> T checkNoEmpty(T input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static <T extends CharSequence> T checkNoEmpty(T input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static <E, T extends Collection<E>> T checkNoEmpty(T input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static <E, T extends Collection<E>> T checkNoEmpty(T input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static <E, T extends Collection<E>> T checkNoEmpty(T input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static <T> T[] checkNoEmpty(T[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static <T> T[] checkNoEmpty(T[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static <T> T[] checkNoEmpty(T[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static boolean[] checkNoEmpty(boolean[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static boolean[] checkNoEmpty(boolean[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static boolean[] checkNoEmpty(boolean[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static char[] checkNoEmpty(char[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static char[] checkNoEmpty(char[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static char[] checkNoEmpty(char[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static byte[] checkNoEmpty(byte[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static byte[] checkNoEmpty(byte[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static byte[] checkNoEmpty(byte[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static short[] checkNoEmpty(short[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static short[] checkNoEmpty(short[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static short[] checkNoEmpty(short[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static int[] checkNoEmpty(int[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static int[] checkNoEmpty(int[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static int[] checkNoEmpty(int[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static long[] checkNoEmpty(long[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static long[] checkNoEmpty(long[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static long[] checkNoEmpty(long[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static float[] checkNoEmpty(float[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static float[] checkNoEmpty(float[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static float[] checkNoEmpty(float[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static double[] checkNoEmpty(double[] input) {
        return Arg.check(Arg.isNoEmpty(input), input, "The input value must be not empty");
    }

    public static double[] checkNoEmpty(double[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoEmpty(input), input, messageTemplate, args);
    }

    public static double[] checkNoEmpty(double[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoEmpty(input), input, messageSupplier);
    }

    public static <E, T extends Collection<E>> T checkElementsNoNull(T input) {
        return Arg.check(Arg.isElementsNoNull(input), input, "The input collection value must be not null");
    }

    public static <E, T extends Collection<E>> T checkElementsNoNull(T input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isElementsNoNull(input), input, messageTemplate, args);
    }

    public static <E, T extends Collection<E>> T checkElementsNoNull(T input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isElementsNoNull(input), input, messageSupplier);
    }

    public static <E> E[] checkElementsNoNull(E[] input) {
        return Arg.check(Arg.isElementsNoNull(input), input, "The input array value must be not null");
    }

    public static <E> E[] checkElementsNoNull(E[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isElementsNoNull(input), input, messageTemplate, args);
    }

    public static <E> E[] checkElementsNoNull(E[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isElementsNoNull(input), input, messageSupplier);
    }

    public static <T extends Collection<? extends CharSequence>> T checkElementsNoEmpty(T input) {
        return Arg.check(Arg.isElementsNoEmpty(input), input, "The input array value must be not empty");
    }

    public static <T extends Collection<? extends CharSequence>> T checkElementsNoEmpty(T input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isElementsNoEmpty(input), input, messageTemplate, args);
    }

    public static <T extends Collection<? extends CharSequence>> T checkElementsNoEmpty(T input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isElementsNoEmpty(input), input, messageSupplier);
    }

    public static <E extends CharSequence> E[] checkElementsNoEmpty(E[] input) {
        return Arg.check(Arg.isElementsNoEmpty(input), input, "The input array value must be not empty");
    }

    public static <E extends CharSequence> E[] checkElementsNoEmpty(E[] input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isElementsNoEmpty(input), input, messageTemplate, args);
    }

    public static <E extends CharSequence> E[] checkElementsNoEmpty(E[] input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isElementsNoEmpty(input), input, messageSupplier);
    }

    public static void checkContain(String input, String ele) {
        Arg.check(Arg.isContain(input, ele), null, "The substring '%s' is not in the string '%s'", ele, input);
    }

    public static void checkContain(String input, String ele, String messageTemplate, Object... args) {
        Arg.check(Arg.isContain(input, ele), null, messageTemplate, args);
    }

    public static void checkContain(String input, String ele, Supplier<String> messageSupplier) {
        Arg.check(Arg.isContain(input, ele), null, messageSupplier);
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
        Arg.check(index >= 0 && index < size, null, "The index must be 0<=index(%d)x<size(%d)", index, size);
    }

    public static void checkElementIndex(int start, int end, int size) {
        Arg.check(start <= end && start >= 0 && end < size, null, "The index must be 0<=start(%d)<=end(%d)<size(%d)", start, end, size);

    }

    public static Duration checkNoNegative(Duration input) {
        return Arg.check(input != null && !input.isNegative(), input, "The value '%s', but the duration cannot be negative", input);
    }

    public static long checkNoNegative(long input) {
        return Arg.check(input >= 0, input, "The value '%s', but it must be no negative", input);
    }

    public static long checkNoNegative(long input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static long checkNoNegative(long input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static int checkNoNegative(int input) {
        return Arg.check(Arg.isNoNegative(input), input, "The value '%s', but it must be no negative", input);
    }

    public static int checkNoNegative(int input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static int checkNoNegative(int input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static short checkNoNegative(short input) {
        return Arg.check(Arg.isNoNegative(input), input, "The value '%s', but it must be no negative", input);
    }

    public static short checkNoNegative(short input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static short checkNoNegative(short input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static byte checkNoNegative(byte input) {
        return Arg.check(Arg.isNoNegative(input), input, "The value '%s', but it must be no negative", input);
    }

    public static byte checkNoNegative(byte input, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNoNegative(input), input, messageTemplate, args);
    }

    public static byte checkNoNegative(byte input, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNoNegative(input), input, messageSupplier);
    }

    public static long checkNatural(long input, long limit) {
        return Arg.check(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static long checkNatural(long input, long limit, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static long checkNatural(long input, long limit, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static int checkNatural(int input, int limit) {
        return Arg.check(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static int checkNatural(int input, int limit, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static int checkNatural(int input, int limit, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static short checkNatural(short input, short limit) {
        return Arg.check(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static short checkNatural(short input, short limit, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static short checkNatural(short input, short limit, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static byte checkNatural(byte input, byte limit) {
        return Arg.check(Arg.isNatural(input, limit), input, "The value '%s', but it must be natural number with limit '%s'", input, limit);
    }

    public static byte checkNatural(byte input, byte limit, String messageTemplate, Object... args) {
        return Arg.check(Arg.isNatural(input, limit), input, messageTemplate, args);
    }

    public static byte checkNatural(byte input, byte limit, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isNatural(input, limit), input, messageSupplier);
    }

    public static long checkRange(long input, long lo, long hi) {
        return Arg.check(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static long checkRange(long input, long lo, long hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static long checkRange(long input, long lo, long hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static int checkRange(int input, int lo, int hi) {
        return Arg.check(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static int checkRange(int input, int lo, int hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static int checkRange(int input, int lo, int hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static short checkRange(short input, short lo, short hi) {
        return Arg.check(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static short checkRange(short input, short lo, short hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static short checkRange(short input, short lo, short hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static byte checkRange(byte input, byte lo, byte hi) {
        return Arg.check(Arg.isRange(input, lo, hi), input, "The value '%s', but it must be '%s<=value<%s'", input, lo, hi);
    }

    public static byte checkRange(byte input, byte lo, byte hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageTemplate, args);
    }

    public static byte checkRange(byte input, byte lo, byte hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isRange(input, lo, hi), input, messageSupplier);
    }

    public static long checkExcludeRange(long input, long lo, long hi) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static long checkExcludeRange(long input, long lo, long hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static long checkExcludeRange(long input, long lo, long hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static int checkExcludeRange(int input, int lo, int hi) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static int checkExcludeRange(int input, int lo, int hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static int checkExcludeRange(int input, int lo, int hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static short checkExcludeRange(short input, short lo, short hi) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static short checkExcludeRange(short input, short lo, short hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static short checkExcludeRange(short input, short lo, short hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static byte checkExcludeRange(byte input, byte lo, byte hi) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, "The value '%s', but it must be 'value<%s' or 'value>=%s'", input, lo, hi);
    }

    public static byte checkExcludeRange(byte input, byte lo, byte hi, String messageTemplate, Object... args) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageTemplate, args);
    }

    public static byte checkExcludeRange(byte input, byte lo, byte hi, Supplier<String> messageSupplier) {
        return Arg.check(Arg.isExcludeRange(input, lo, hi), input, messageSupplier);
    }

    public static void checkCompatibilityFrom(Class<?> inputClass, Class<?> matchClass) {
        Arg.check(Arg.isCompatibilityFrom(inputClass, matchClass), null, "This match type '%s' is not compatible with input type '%s'", matchClass, inputClass);
    }

    public static void checkCompatibilityFrom(Class<?> inputClass, Class<?> matchClass, String messageTemplate, Object... args) {
        Arg.check(Arg.isCompatibilityFrom(inputClass, matchClass), null, messageTemplate, args);
    }

    public static void checkCompatibilityFrom(Class<?> inputClass, Class<?> matchClass, Supplier<String> messageSupplier) {
        Arg.check(Arg.isCompatibilityFrom(inputClass, matchClass), null, messageSupplier);
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
            if (input instanceof CharSequence) return ZoneId.of(input.toString());
            else if (input instanceof ZoneId) return (ZoneId) input;
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
}
