package potatoxf.infrastructure;


import sun.misc.Unsafe;

import java.lang.reflect.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 公共函数库，主要包括经常使用的各种各样的静态函数
 * <p/>
 * Create Time:2024-04-14
 *
 * @author potatoxf
 */
public final class Com {
    /**
     * The Unsafe class
     */
    private static final Class<Unsafe> UNSAFE_CLASS = Unsafe.class;
    /**
     * The Unsafe instance
     */
    private static final Unsafe UNSAFE_INSTANCE = safeGetUnsafe();
    /**
     * A reference to Throwable initCause method
     */
    private static final Method INIT_CAUSE_METHOD = Com.safeGetMethod(false, false, Throwable.class, "initCause", Throwable.class);
    /**
     * A reference to Throwable detailMessage field
     */
    private static final Field DETAIL_MESSAGE_FIELD = Com.safeGetField(false, false, Throwable.class, "detailMessage");
    /**
     * 缓存公共构造函数
     */
    private static final Map<Class<?>, Constructor<?>[]> CACHE_CONSTRUCTOR = new ConcurrentHashMap<>();

    /**
     * 构建字符串，用于{@link Object#toString()}
     *
     * @param args 输入参数，如果是奇数：prefix,key1,value1,key2,value2，如果是偶数，key1,value1,key2,value2
     * @return 返回构建的字符串 {@link String}
     */
    public static String buildToString(Object... args) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (args.length % 2 != 0) {
            sb.append(args[0]);
            i++;
        }
        sb.append('{');
        while (i < args.length) {
            sb.append(args[i++]).append('=').append(args[i++]).append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 生成填充类属性类
     *
     * @param start 开始索引
     * @param bytes 生成字节数
     * @return 返回字符串
     */
    public static String generateClassPadding(int start, int bytes) {
        start = Math.max(start, 0);
        bytes = Math.max(bytes, 4);
        int c = 0;
        for (int j = start + bytes - 1; j != 0; j /= 10) c++;
        c = Math.max(3, c);
        StringBuilder stringBuilder = new StringBuilder(17 * bytes);
        for (int i = 0; i < bytes; i++) {
            if ((i % 4) == 0) {
                stringBuilder.append("byte ");
            }
            stringBuilder.append(String.format("__padding_%0" + c + "d", start + i));

            if ((i + 1) % 4 == 0) {
                stringBuilder.append(";\n");
            } else {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 判断JDK版本
     *
     * @return 判断是否是1.4之前的JVM
     */
    public static boolean isMatchJVM(int integer, int decimal, boolean before, boolean includeEquals) {
        integer = Math.max(integer, 1);
        decimal = Math.max(decimal, 0);
        final String specVersion = System.getProperty("java.specification.version");
        int i = specVersion.indexOf('.');
        int specVersionInteger, specVersionDecimal = 0;
        if (i >= 0) {
            specVersionInteger = Integer.parseInt(specVersion.substring(0, i));
            specVersionDecimal = Integer.parseInt(specVersion.substring(i + 1));
        } else {
            specVersionInteger = Integer.parseInt(specVersion);
        }
        if (specVersionInteger < integer) return before;
        if (specVersionInteger > integer) return !before;
        if (specVersionDecimal < decimal) return before;
        if (specVersionDecimal > decimal) return !before;
        return includeEquals;
    }

    /**
     * 初始化异常原因
     *
     * @param exception 异常
     * @param cause     异常原因
     * @return 如果初始化返回true，否则返回false
     */
    public static boolean initExceptionCause(Throwable exception, Throwable cause) {
        if (INIT_CAUSE_METHOD != null && cause != null) {
            try {
                INIT_CAUSE_METHOD.invoke(exception, cause);
                return true;
            } catch (Throwable e) {
                return false; // can't initialize cause
            }
        }
        return false;
    }

    /**
     * 初始化异常信息
     *
     * @param exception     异常
     * @param detailMessage 异常信心
     * @return 如果初始化返回true，否则返回false
     */
    public static boolean initExceptionMessage(Throwable exception, String detailMessage) {
        if (DETAIL_MESSAGE_FIELD != null && detailMessage != null) {
            try {
                DETAIL_MESSAGE_FIELD.set(exception, detailMessage);
                return true;
            } catch (Throwable e) {
                return false; // can't initialize cause
            }
        }
        return false;
    }

    /**
     * 创建实例
     *
     * @param type 实例类型
     * @param args 参数
     * @param <T>  对象类型
     * @return 创建对象
     * @throws ReflectiveOperationException 如果反射构造对象抛出异常
     */
    public static <T> T newInstance(String type, Object... args) throws ReflectiveOperationException {
        return Com.newInstance(Com.safeGetClass(true, type), args);
    }

    /**
     * 创建实例
     *
     * @param type 实例类型
     * @param args 参数
     * @param <T>  对象类型
     * @return 创建对象
     * @throws ReflectiveOperationException 如果反射构造对象抛出异常
     */
    public static <T> T newInstance(Class<T> type, Object... args) throws ReflectiveOperationException {
        int modifiers = type.getModifiers();
        if (Modifier.isAbstract(modifiers)) {
            throw new IllegalAccessException("The class [" + type + " ] is an abstract class");
        }
        if (!Modifier.isPublic(modifiers)) {
            throw new IllegalAccessException("The class [" + type + "] is not a public class");
        }
        Constructor<?>[] constructors = CACHE_CONSTRUCTOR.computeIfAbsent(type, Class::getDeclaredConstructors);
        if (constructors == null || constructors.length == 0) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + type.getName());
        }
        Constructor<?> constructor = null;
        for (Constructor<?> ctor : constructors) {
            if (ctor.getParameterCount() != args.length) continue;
            Class<?>[] parameterTypes = ctor.getParameterTypes();
            boolean match = true;
            for (int n = 0; n < args.length; n++) {
                Class<?> argType = args[n] == null ? null : args[n].getClass();
                if (Arg.isCompatibilityFrom(argType, parameterTypes[n])) continue;
                match = false;
                break;
            }
            if (match) {
                constructor = ctor;
                break;
            }
        }
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + type.getName());
        }
        //noinspection unchecked
        return (T) Com.safeSetAccessible(constructor).newInstance(args);
    }

    /**
     * 随机休眠
     *
     * @param maxMillis 最大毫秒数
     * @return 返回随机休眠时间
     */
    public static int sleepWithRandom(int maxMillis) {
        return Com.sleepWithRandom(0, maxMillis);
    }

    /**
     * 随机休眠
     *
     * @param maxMillisecond 最大毫秒数
     * @param minMillisecond 最小毫秒数
     */
    public static int sleepWithRandom(int minMillisecond, int maxMillisecond) {
        int minMillis = Math.max(Math.min(minMillisecond, maxMillisecond), 0);
        int maxMillis = Math.max(Math.max(minMillisecond, maxMillisecond), 0);
        int sleepTime;
        if (maxMillis == minMillis) {
            sleepTime = minMillis;
        } else {
            sleepTime = ThreadLocalRandom.current().nextInt(Math.abs(maxMillisecond - minMillis)) + minMillis;
        }
        Com.sleep(sleepTime);
        return sleepTime;
    }

    /**
     * 休眠
     *
     * @param millis 毫秒数
     */
    public static void sleep(long millis) {
        try {
            if (Log.isEnabledTrace()) {
                Thread thread = Thread.currentThread();
                Log.trace("The [" + thread + "] going to ready to rest " + millis + " millisecond");
            }
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            if (Log.isEnabledError()) {
                Thread thread = Thread.currentThread();
                Log.error(thread + " was interrupted when it is resting", e);
            }
        }
    }

    /**
     * 对 {@link Unsafe} 的静态访问和用于执行低级别、不安全操作的便捷实用方法。
     *
     * @return {@link Unsafe}
     */
    public static Unsafe safeGetUnsafe() {
        if (UNSAFE_INSTANCE != null) return UNSAFE_INSTANCE;
        try {
            Field field = safeGetField(false, false, UNSAFE_CLASS, "THE_ONE");
            if (field == null) {
                field = safeGetField(false, false, UNSAFE_CLASS, "theUnsafe");
            }
            if (field == null) {
                return newInstance(UNSAFE_CLASS);
            }
            return (Unsafe) field.get(null);
        } catch (Throwable e) {
            throw new Error("Failed to load sun.misc.Unsafe", e);
        }
    }

    /**
     * 返回给定对象字段的位置。
     *
     * @param clazz     包含字段的类
     * @param fieldName 字段的名称
     * @return 字段的地址偏移量
     */
    public static long safeGetObjectFieldOffset(Class<?> clazz, String fieldName) {
        return Com.safeGetObjectFieldOffset(true, clazz, fieldName);
    }

    /**
     * 返回给定对象字段的位置。
     *
     * @param isThrow   是否抛出移除
     * @param clazz     包含字段的类
     * @param fieldName 字段的名称
     * @return 字段的地址偏移量
     */
    public static long safeGetObjectFieldOffset(boolean isThrow, Class<?> clazz, String fieldName) {
        return UNSAFE_INSTANCE.objectFieldOffset(Com.safeGetField(isThrow, false, clazz, fieldName));
    }

    /**
     * 返回给定静态字段的位置。
     *
     * @param clazz     包含字段的类
     * @param fieldName 字段的名称
     * @return 字段的地址偏移量
     */
    public static long safeGetStaticFieldOffset(Class<?> clazz, String fieldName) {
        return Com.safeGetStaticFieldOffset(true, clazz, fieldName);
    }

    /**
     * 返回给定静态字段的位置。
     *
     * @param isThrow   是否抛出移除
     * @param clazz     包含字段的类
     * @param fieldName 字段的名称
     * @return 字段的地址偏移量
     */
    public static long safeGetStaticFieldOffset(boolean isThrow, Class<?> clazz, String fieldName) {
        return UNSAFE_INSTANCE.staticFieldOffset(Com.safeGetField(isThrow, false, clazz, fieldName));
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> safeGetClass(Class<?> input) {
        return Objects.requireNonNull((Class<T>) input, "The input class must be not null");
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> safeGetClass(boolean isThrow, String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to getting class from '" + className + "'");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> safeGetClass(boolean isThrow, String className, boolean initialize, ClassLoader loader) {
        try {
            return (Class<T>) Class.forName(className, initialize, loader);
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to getting class from '" + className + "'");
        }
    }

    public static Constructor<?> safeGetConstructor(boolean isThrow, boolean isMustPublic, String type, Class<?>... args) {
        return Com.safeGetConstructor(isThrow, isMustPublic, Com.safeGetClass(isThrow, type), args);
    }

    public static Constructor<?> safeGetConstructor(boolean isThrow, boolean isMustPublic, Class<?> type, Class<?>... args) {
        try {
            type = Com.safeGetClass(type);
            return Com.safeSetAccessible(isMustPublic ? type.getConstructor(args) : type.getDeclaredConstructor(args));
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to calling '" + type + "#get" + (isMustPublic ? "" : "Declared") + "Constructor' for getting 'Constructor'");
        }
    }

    public static Method safeGetMethod(boolean isThrow, boolean isMustPublic, String type, String methodName, Class<?>... args) {
        return Com.safeGetMethod(isThrow, isMustPublic, Com.safeGetClass(isThrow, type), methodName, args);
    }

    public static Method safeGetMethod(boolean isThrow, boolean isMustPublic, Class<?> type, String methodName, Class<?>... args) {
        try {
            type = Com.safeGetClass(type);
            return Com.safeSetAccessible(isMustPublic ? type.getMethod(methodName, args) : type.getDeclaredMethod(methodName, args));
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to calling '" + type + "#get" + (isMustPublic ? "" : "Declared") + "Method' for getting 'Method'");
        }
    }

    public static Field safeGetField(boolean isThrow, boolean isMustPublic, String type, String fieldName) {
        return Com.safeGetField(isThrow, isMustPublic, Com.safeGetClass(isThrow, type), fieldName);
    }

    public static Field safeGetField(boolean isThrow, boolean isMustPublic, Class<?> type, String fieldName) {
        try {
            type = Com.safeGetClass(type);
            return Com.safeSetAccessible(isMustPublic ? type.getField(fieldName) : type.getDeclaredField(fieldName));
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to calling '" + type + "#get" + (isMustPublic ? "" : "Declared") + "Field' for getting 'Field'");
        }
    }

    public static <T extends AccessibleObject & Member> T safeSetAccessible(T input) {
        if (!Modifier.isPublic(input.getModifiers())) {
            input.setAccessible(true);
        }
        return input;
    }
}
