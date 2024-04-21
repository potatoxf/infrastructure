package potatoxf.infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
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
     * A reference to Throwable initCause method
     */
    private static final Method INIT_CAUSE_METHOD;
    /**
     * A reference to Throwable detailMessage field
     */
    private static final Field DETAIL_MESSAGE_FIELD;
    /**
     * 缓存公共构造函数
     */
    private static final Map<Class<?>, Constructor<?>[]> CACHE_CONSTRUCTOR = new ConcurrentHashMap<>();

    static {
        Method method;
        try {
            method = Throwable.class.getMethod("initCause", Throwable.class);
            method.setAccessible(true);
        } catch (Throwable e) {
            if (Log.isEnabledWarn()) {
                Log.warn("Error getting the Throwable 'initCause()' method", e);
            }
            method = null;
        }
        INIT_CAUSE_METHOD = method;
        Field field;
        try {
            field = Throwable.class.getDeclaredField("detailMessage");
            field.setAccessible(true);
        } catch (Throwable e) {
            if (Log.isEnabledWarn()) {
                Log.warn("Error getting the Throwable 'detailMessage' field", e);
            }
            field = null;
        }
        DETAIL_MESSAGE_FIELD = field;

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
    public static <T> T newInstance(Class<T> type, Object... args) throws ReflectiveOperationException {
        int modifiers = type.getModifiers();
        if (Modifier.isAbstract(modifiers)) {
            throw new IllegalAccessException("The class [" + type + " ] is an abstract class");
        }
        if (!Modifier.isPublic(modifiers)) {
            throw new IllegalAccessException("The class [" + type + "] is not a public class");
        }
        Constructor<?>[] constructors = CACHE_CONSTRUCTOR.computeIfAbsent(type, Class::getConstructors);
        if (constructors == null || constructors.length == 0) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + type.getName());
        }
        Constructor<T> constructor = null;
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
                //noinspection unchecked
                constructor = (Constructor<T>) ctor;
            }
        }
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + type.getName());
        }
        return constructor.newInstance(args);
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
}
