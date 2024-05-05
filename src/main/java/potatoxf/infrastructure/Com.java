package potatoxf.infrastructure;


import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;

/**
 * 公共函数库，主要包括经常使用的各种各样的静态函数
 * <p/>
 * Create Time:2024-04-14
 *
 * @author potatoxf
 */
@SuppressWarnings("unchecked")
public final class Com {
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
    private static final ConcurrentHashMap<Class<?>, Constructor<?>[]> CACHE_CONSTRUCTOR = new ConcurrentHashMap<>();

    /**
     * 乐观同步读取
     *
     * @param stampedLock cas锁
     * @param reader      读取动作
     * @param <T>         读取结果类型
     * @return 返回读取结果
     */
    public static <T> T syncOptimisticRead(StampedLock stampedLock, Supplier<T> reader) {
        long l = stampedLock.tryOptimisticRead();
        T result;
        if (stampedLock.validate(l)) {
            result = reader.get();
        } else {
            l = stampedLock.readLock();
            try {
                result = reader.get();
            } finally {
                stampedLock.unlockRead(l);
            }
        }
        return result;
    }

    /**
     * 同步读取
     *
     * @param stampedLock cas锁
     * @param reader      读取动作
     * @param <T>         读取结果类型
     * @return 返回读取结果
     */
    public static <T> T syncRead(StampedLock stampedLock, Supplier<T> reader) {
        long l = stampedLock.readLock();
        try {
            return reader.get();
        } finally {
            stampedLock.unlockRead(l);
        }
    }

    /**
     * 同步写入
     *
     * @param stampedLock cas锁
     * @param writer      写入动作
     * @param <T>         写入结果类型
     * @return 返回写入结果
     */
    public static <T> T syncWrite(StampedLock stampedLock, Supplier<T> writer) {
        long l = stampedLock.writeLock();
        try {
            return writer.get();
        } finally {
            stampedLock.unlockWrite(l);
        }
    }

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
            Object o = args[i++];
            if (o != null) {
                sb.append(o);
            } else {
                sb.append(" ");
            }
            sb.append('=');
            o = args[i++];
            if (o != null) {
                sb.append(o);
            } else {
                sb.append(" ");
            }
            sb.append(',');
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
     * 获取 {@link ResourceBundle}
     *
     * @param isThrow      是否抛出移除
     * @param i18nBaseName 国际化语言资源包基础名
     * @param locale       国际化语言位置
     * @return 返回 {@link ResourceBundle}
     */
    public static ResourceBundle safeGetResourceBundle(boolean isThrow, String i18nBaseName, Locale locale) {
        try {
            return locale == null ? ResourceBundle.getBundle(i18nBaseName) : ResourceBundle.getBundle(i18nBaseName, locale);
        } catch (MissingResourceException e) {
            return Log.warnOrThrowError(isThrow, e, "Error to getting resource from '" + i18nBaseName + ".properties'" + (locale != null ? "with '" + locale + "'" : ""));
        }
    }

    public static void safeClose(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable == null) continue;
            if (closeable instanceof Flushable) {
                try {
                    ((Flushable) closeable).flush();
                } catch (IOException e) {
                    // ignore
                }
            }
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public static <T> Class<T> safeGetClass(Class<?> input) {
        return Objects.requireNonNull((Class<T>) input, "The input class must be not null");
    }

    public static <T> Class<T> safeGetClass(boolean isThrow, String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to getting class from '" + className + "'");
        }
    }

    public static <T> Class<T> safeGetClass(boolean isThrow, String className, ClassLoader loader) {
        return Com.safeGetClass(isThrow, className, true, loader);
    }

    public static <T> Class<T> safeGetClass(boolean isThrow, String className, boolean initialize, ClassLoader loader) {
        try {
            return (Class<T>) Class.forName(className, initialize, loader);
        } catch (Throwable e) {
            return Log.warnOrThrowError(isThrow, e, "Error to getting class from '" + className + "'");
        }
    }

    public static <T> Class<T> safeGetClassWithSystem(boolean isThrow, String className) {
        return Com.safeGetClass(isThrow, className, true, ClassLoader.getSystemClassLoader());
    }

    public static <T> Class<T> safeGetClassWithSystem(boolean isThrow, String className, boolean initialize) {
        return Com.safeGetClass(isThrow, className, initialize, ClassLoader.getSystemClassLoader());
    }

    public static String safeGetClassName(Class<?> clz) {
        // We want a human-readable class name. getName() returns JVM signature.
        // getCanonicalName() returns proper string, unless it is hits the bug.
        // If it fails, then we will fall back to getName()
        //   https://bugs.openjdk.java.net/browse/JDK-8057919
        try {
            String n = clz.getCanonicalName();
            if (n != null) {
                return n;
            }
        } catch (Throwable e) {
            // fall-through
        }
        return clz.getName();
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
            return Log.warnOrThrowError(isThrow, e, "Error to calling '" + type + "#get" + (isMustPublic ? "" : "Declared") + "Method' for getting method of '" + methodName + "'");
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
            return Log.warnOrThrowError(isThrow, e, "Error to calling '" + type + "#get" + (isMustPublic ? "" : "Declared") + "Field' for getting field of '" + fieldName + "'");
        }
    }

    public static <T extends AccessibleObject & Member> T safeSetAccessible(T input) {
        if (!Modifier.isPublic(input.getModifiers())) {
            input.setAccessible(true);
        }
        return input;
    }

    private static String safeGetVMOption(String key) throws Throwable {
        javax.management.MBeanServer server = java.lang.management.ManagementFactory.getPlatformMBeanServer();
        javax.management.ObjectName mbean = new javax.management.ObjectName("com.sun.management:type=HotSpotDiagnostic");
        Object vmOption = server.invoke(mbean, "getVMOption", new Object[]{key}, new String[]{"java.lang.String"});
        return ((javax.management.openmbean.CompositeDataSupport) vmOption).get("value").toString();
    }

    public static Boolean safeGetVMOptionForCompressedOops() {
        try {
            String useCompressedOops = Com.safeGetVMOption("UseCompressedOops");
            if (useCompressedOops != null) return Boolean.parseBoolean(useCompressedOops);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static Boolean safeGetVMOptionForCompressedClassPointers() {
        try {
            String useCompressedClassPointers = Com.safeGetVMOption("UseCompressedClassPointers");
            if (useCompressedClassPointers != null) return Boolean.parseBoolean(useCompressedClassPointers);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static Integer safeGetVMOptionForObjectAlignment() {
        if (Boolean.TRUE.equals(Com.safeGetVMOptionForCompressedOops())) {
            try {
                String objectAlignmentInBytes = Com.safeGetVMOption("ObjectAlignmentInBytes");
                if (objectAlignmentInBytes != null) return Integer.parseInt(objectAlignmentInBytes);
            } catch (Throwable ignored) {
            }
        }
        return null;
    }
}
