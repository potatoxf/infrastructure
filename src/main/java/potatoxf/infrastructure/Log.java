package potatoxf.infrastructure;

import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 日志处理操作
 * <p>
 * 默认日志器{@link ConsoleFactory}，使用的是用{@code System.out}和{@code System.err}
 * 其它内置还支持JCL,log4j,log4j2,slf4j。使用时需要引用依赖即可
 * <p>
 * 日志对象{@link Log}构造指定日志器的ID{@link #of(String)}，使用如下对象方法输出日志：
 * 如果打开了{@link #isEnabledError()}则{@link #error(String, Object...)}会输出日志
 * 如果打开了{@link #isEnabledWarn()}则{@link #warn(String, Object...)}会输出日志
 * 如果打开了{@link #isEnabledInfo()}则{@link #info(String, Object...)}会输出日志
 * 如果打开了{@link #isEnabledDebug()}则{@link #debug(String, Object...)}会输出日志
 * 如果打开了{@link #isEnabledTrace()}则{@link #trace(String, Object...)}会输出日志
 * <p>
 * 静态日志{@link Log}，使用全局的日志器，可以通过{@link #setGlobal(String)}设置指定日志器的ID，使用如下方法输出日志：
 * 如果打开了{@link #enabledDebug()}则{@link #error(String, Object...)}会输出日志
 * 如果打开了{@link #enabledWarn()}则{@link #warn(String, Object...)}会输出日志
 * 如果打开了{@link #enabledInfo()}则{@link #info(String, Object...)}会输出日志
 * 如果打开了{@link #enabledDebug()}则{@link #debug(String, Object...)}会输出日志
 * 如果打开了{@link #enabledTrace()}则{@link #trace(String, Object...)}会输出日志
 *
 * @author potatoxf
 */
public final class Log {
    /**
     * 本对象日志全限定名
     */
    private static final String LOG_CLASS_NAME = Log.class.getName();
    /**
     * 缓存的
     */
    private static final Map<String, Factory> FACTORY_MAP = new HashMap<>(10, 1);
    /**
     * 默认构造工厂
     */
    private static final Factory DEFAULT_FACTORY = new ConsoleFactory();
    /**
     * 默认日志
     */
    private static final Log DEFAULT_LOG = new Log(null);
    /**
     * 全局日志
     */
    private static final AtomicReference<Log> GLOBAL_LOG = new AtomicReference<>(DEFAULT_LOG);
    /**
     * 日志ID
     */
    private final String id;

    static {
        FACTORY_MAP.put(null, new ConsoleFactory());
        FACTORY_MAP.put("jcl", new JCLFactory());
        FACTORY_MAP.put("log4j", new Log4jFactory());
        FACTORY_MAP.put("log4j2", new Log4j2Factory());
        FACTORY_MAP.put("slf4j", new Slf4jFactory());
    }

    /**
     * 创建{@link Log}
     *
     * @param id 日志ID
     * @return {@link Log}
     */
    public static Log of(String id) {
        check(id, true);
        if (id == null) return DEFAULT_LOG;
        return new Log(id);
    }

    /**
     * 设置全局日志
     *
     * @param id 日志ID
     * @return 如果设置成功返回true，否则返回false
     */
    public static boolean setGlobal(String id) {
        if (check(id, false)) {
            if (!Objects.equals(global().id, id)) {
                GLOBAL_LOG.set(new Log(id));
            }
            return true;
        }
        return false;
    }

    /**
     * 添加r日志工厂
     *
     * @param id      日志ID
     * @param factory 日志工厂
     * @return 如果设置成功返回true，否则返回false
     */
    public static boolean addFactory(String id, Factory factory) {
        if (id != null && factory != null) {
            FACTORY_MAP.put(id, factory);
            return true;
        }
        return false;
    }

    public static boolean isEnabledTrace() {
        return global().enabledTrace();
    }

    public static boolean isEnabledDebug() {
        return global().enabledDebug();
    }

    public static boolean isEnabledInfo() {
        return global().enabledInfo();
    }

    public static boolean isEnabledWarn() {
        return global().enabledWarn();
    }

    public static boolean isEnabledError() {
        return global().enabledError();
    }

    public static void trace(String messageTemplate, Object... args) {
        global().logTrace(messageTemplate, args);
    }

    public static void debug(String messageTemplate, Object... args) {
        global().logDebug(messageTemplate, args);
    }

    public static void info(String messageTemplate, Object... args) {
        global().logInfo(messageTemplate, args);
    }

    public static void warn(String messageTemplate, Object... args) {
        global().logWarn(messageTemplate, args);
    }

    public static void error(String messageTemplate, Object... args) {
        global().logError(messageTemplate, args);
    }

    public static <T> T traceOrThrowError(boolean isThrows, Throwable exception, String msg) {
        if (isThrows) {
            throw new Error(msg, exception);
        } else {
            if (Log.isEnabledTrace()) {
                Log.trace(msg, exception);
            }
            return null;
        }
    }

    public static <T> T debugOrThrowError(boolean isThrows, Throwable exception, String msg) {
        if (isThrows) {
            throw new Error(msg, exception);
        } else {
            if (Log.isEnabledDebug()) {
                Log.debug(msg, exception);
            }
            return null;
        }
    }

    public static <T> T infoOrThrowError(boolean isThrows, Throwable exception, String msg) {
        if (isThrows) {
            throw new Error(msg, exception);
        } else {
            if (Log.isEnabledInfo()) {
                Log.info(msg, exception);
            }
            return null;
        }
    }

    public static <T> T warnOrThrowError(boolean isThrows, Throwable exception, String msg) {
        if (isThrows) {
            throw new Error(msg, exception);
        } else {
            if (Log.isEnabledWarn()) {
                Log.warn(msg, exception);
            }
            return null;
        }
    }

    public static <T> T errorOrThrowError(boolean isThrows, Throwable exception, String msg) {
        if (isThrows) {
            throw new Error(msg, exception);
        } else {
            if (Log.isEnabledError()) {
                Log.error(msg, exception);
            }
            return null;
        }
    }

    private static boolean check(String id, boolean isThrowException) {
        Factory factory = FACTORY_MAP.get(id);
        if (factory == null) {
            if (!isThrowException) return false;
            else throw new IllegalArgumentException("There is no log implementation factory for [" + id + "].");
        }
        if (factory.notSupport()) {
            if (!isThrowException) return false;
            else
                throw new IllegalArgumentException("The log implementation factory that does not implement [" + id + "].");
        }
        return true;
    }

    private static Log global() {
        return GLOBAL_LOG.get();
    }

    private static StackTraceElement getCallStackTraceElement() {
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        int i = 5;
        while (i < s.length && LOG_CLASS_NAME.equals(s[i].getClassName())) i++;
        return s[i];
    }

    private Log(String id) {
        this.id = id;
    }

    public boolean enabledTrace() {
        return FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().isEnabledFor(Lv.TRACE);
    }

    public boolean enabledDebug() {
        return FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().isEnabledFor(Lv.DEBUG);
    }

    public boolean enabledInfo() {
        return FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().isEnabledFor(Lv.INFO);
    }

    public boolean enabledWarn() {
        return FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().isEnabledFor(Lv.WARN);
    }

    public boolean enabledError() {
        return FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().isEnabledFor(Lv.ERROR);
    }

    public void logTrace(String messageTemplate, Object... args) {
        FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().printLog(Lv.TRACE, messageTemplate, args);
    }

    public void logDebug(String messageTemplate, Object... args) {
        FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().printLog(Lv.DEBUG, messageTemplate, args);
    }

    public void logInfo(String messageTemplate, Object... args) {
        FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().printLog(Lv.INFO, messageTemplate, args);
    }

    public void logWarn(String messageTemplate, Object... args) {
        FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().printLog(Lv.WARN, messageTemplate, args);
    }

    public void logError(String messageTemplate, Object... args) {
        FACTORY_MAP.getOrDefault(id, DEFAULT_FACTORY).getJournal().printLog(Lv.ERROR, messageTemplate, args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Log other = (Log) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return id;
    }

    private static class Slf4jFactory extends Factory {
        Method[] levels, methods;
        Class<?> loggerFacotryClass, loggerClass;
        Method getLogger;

        @Override
        protected String name() {
            return "Slf4j";
        }

        protected boolean init() throws Throwable {
            loggerFacotryClass = Class.forName("org.slf4j.LoggerFactory");
            loggerClass = Class.forName("org.slf4j.Logger");

            getLogger = loggerFacotryClass.getDeclaredMethod("getLogger", String.class);

            levels = new Method[5];
            levels[Lv.TRACE.ordinal()] = loggerClass.getDeclaredMethod("isTraceEnabled");
            levels[Lv.DEBUG.ordinal()] = loggerClass.getDeclaredMethod("isDebugEnabled");
            levels[Lv.INFO.ordinal()] = loggerClass.getDeclaredMethod("isInfoEnabled");
            levels[Lv.WARN.ordinal()] = loggerClass.getDeclaredMethod("isWarnEnabled");
            levels[Lv.ERROR.ordinal()] = loggerClass.getDeclaredMethod("isErrorEnabled");

            methods = new Method[5];
            methods[Lv.TRACE.ordinal()] = loggerClass.getMethod("trace", String.class, Throwable.class);
            methods[Lv.DEBUG.ordinal()] = loggerClass.getMethod("debug", String.class, Throwable.class);
            methods[Lv.INFO.ordinal()] = loggerClass.getMethod("info", String.class, Throwable.class);
            methods[Lv.WARN.ordinal()] = loggerClass.getMethod("warn", String.class, Throwable.class);
            methods[Lv.ERROR.ordinal()] = loggerClass.getMethod("error", String.class, Throwable.class);

            return true;
        }

        @Override
        protected Object newJournal(String className) throws Throwable {
            return getLogger.invoke(null, className);
        }

        @Override
        protected boolean isEnabledFor(Object lo, Lv lv) throws Throwable {
            return (boolean) levels[lv.ordinal()].invoke(lo);
        }

        @Override
        protected void log(Object lo, Lv lv, StackTraceElement ste, String message, Throwable throwable) throws Throwable {
            methods[lv.ordinal()].invoke(lo, ste.toString() + " " + message, throwable);
        }
    }

    private static class Log4j2Factory extends Factory {
        Method[] levels, methods;
        Class<?> loggerFacotryClass, loggerClass;
        Method getLogger;

        @Override
        protected String name() {
            return "Log4j2";
        }

        protected boolean init() throws Throwable {
            loggerFacotryClass = Class.forName("org.apache.logging.log4j.LogManager");
            loggerClass = Class.forName("org.apache.logging.log4j.Logger");

            getLogger = loggerFacotryClass.getDeclaredMethod("getLogger", String.class);

            levels = new Method[5];
            levels[Lv.TRACE.ordinal()] = loggerClass.getDeclaredMethod("isTraceEnabled");
            levels[Lv.DEBUG.ordinal()] = loggerClass.getDeclaredMethod("isDebugEnabled");
            levels[Lv.INFO.ordinal()] = loggerClass.getDeclaredMethod("isInfoEnabled");
            levels[Lv.WARN.ordinal()] = loggerClass.getDeclaredMethod("isWarnEnabled");
            levels[Lv.ERROR.ordinal()] = loggerClass.getDeclaredMethod("isErrorEnabled");

            methods = new Method[5];
            methods[Lv.TRACE.ordinal()] = loggerClass.getMethod("trace", Object.class, Throwable.class);
            methods[Lv.DEBUG.ordinal()] = loggerClass.getMethod("debug", Object.class, Throwable.class);
            methods[Lv.INFO.ordinal()] = loggerClass.getMethod("info", Object.class, Throwable.class);
            methods[Lv.WARN.ordinal()] = loggerClass.getMethod("warn", Object.class, Throwable.class);
            methods[Lv.ERROR.ordinal()] = loggerClass.getMethod("error", Object.class, Throwable.class);

            return true;
        }

        @Override
        protected Object newJournal(String className) throws Throwable {
            return getLogger.invoke(null, className);
        }

        @Override
        protected boolean isEnabledFor(Object lo, Lv lv) throws Throwable {
            return (boolean) levels[lv.ordinal()].invoke(lo);
        }

        @Override
        protected void log(Object lo, Lv lv, StackTraceElement ste, String message, Throwable throwable) throws Throwable {
            methods[lv.ordinal()].invoke(lo, ste.toString() + " " + message, throwable);
        }
    }

    private static class Log4jFactory extends Factory {
        Object[] levels = new Object[5];
        Method[] methods = new Method[5];
        Class<?> loggerFacotryClass, loggerClass, levelClass, priorityClass;
        Method getLogger, isEnabledFor;

        @Override
        protected String name() {
            return "Log4j";
        }

        protected boolean init() throws Throwable {
            loggerFacotryClass = Class.forName("org.apache.log4j.Logger");
            loggerClass = Class.forName("org.apache.log4j.Logger");
            levelClass = Class.forName("org.apache.log4j.Level");
            priorityClass = Class.forName("org.apache.log4j.Priority");
            getLogger = loggerClass.getDeclaredMethod("getLogger", String.class);
            isEnabledFor = loggerClass.getMethod("isEnabledFor", priorityClass);

            levels = new Object[5];
            levels[Lv.TRACE.ordinal()] = levelClass.getDeclaredField("TRACE");
            levels[Lv.DEBUG.ordinal()] = levelClass.getDeclaredField("DEBUG");
            levels[Lv.INFO.ordinal()] = levelClass.getDeclaredField("INFO");
            levels[Lv.WARN.ordinal()] = levelClass.getDeclaredField("WARN");
            levels[Lv.ERROR.ordinal()] = levelClass.getDeclaredField("ERROR");

            methods = new Method[5];
            methods[Lv.TRACE.ordinal()] = loggerClass.getMethod("trace", Object.class, Throwable.class);
            methods[Lv.DEBUG.ordinal()] = loggerClass.getMethod("debug", Object.class, Throwable.class);
            methods[Lv.INFO.ordinal()] = loggerClass.getMethod("info", Object.class, Throwable.class);
            methods[Lv.WARN.ordinal()] = loggerClass.getMethod("warn", Object.class, Throwable.class);
            methods[Lv.ERROR.ordinal()] = loggerClass.getMethod("error", Object.class, Throwable.class);

            return true;
        }

        @Override
        protected Object newJournal(String className) throws Throwable {
            return getLogger.invoke(null, className);
        }

        @Override
        protected boolean isEnabledFor(Object lo, Lv lv) throws Throwable {
            return (boolean) isEnabledFor.invoke(lo, levels[lv.ordinal()]);
        }

        @Override
        protected void log(Object lo, Lv lv, StackTraceElement ste, String message, Throwable throwable) throws Throwable {
            methods[lv.ordinal()].invoke(lo, ste.toString() + " " + message, throwable);
        }
    }

    private static class JCLFactory extends Factory {
        @Override
        protected String name() {
            return "Java Common Log";
        }

        @Override
        protected Object newJournal(String className) {
            return java.util.logging.Logger.getLogger(className);
        }

        @Override
        protected boolean isEnabledError(Object lo) {
            return ((java.util.logging.Logger) lo).isLoggable(java.util.logging.Level.SEVERE);
        }

        @Override
        protected boolean isEnabledWarn(Object lo) {
            return ((java.util.logging.Logger) lo).isLoggable(java.util.logging.Level.WARNING);
        }

        @Override
        protected boolean isEnabledInfo(Object lo) {
            return ((java.util.logging.Logger) lo).isLoggable(java.util.logging.Level.INFO);
        }

        @Override
        protected boolean isEnabledDebug(Object lo) {
            return ((java.util.logging.Logger) lo).isLoggable(java.util.logging.Level.CONFIG);
        }

        @Override
        protected boolean isEnabledTrace(Object lo) {
            return ((java.util.logging.Logger) lo).isLoggable(java.util.logging.Level.FINE);
        }

        @Override
        protected void error(Object lo, StackTraceElement ste, String message, Throwable throwable) {
            ((java.util.logging.Logger) lo).logp(java.util.logging.Level.SEVERE, ste.getClassName(), ste.getMethodName(), message, throwable);
        }

        @Override
        protected void warn(Object lo, StackTraceElement ste, String message, Throwable throwable) {
            ((java.util.logging.Logger) lo).logp(java.util.logging.Level.WARNING, ste.getClassName(), ste.getMethodName(), message, throwable);
        }

        @Override
        protected void info(Object lo, StackTraceElement ste, String message, Throwable throwable) {
            ((java.util.logging.Logger) lo).logp(java.util.logging.Level.INFO, ste.getClassName(), ste.getMethodName(), message, throwable);
        }

        @Override
        protected void debug(Object lo, StackTraceElement ste, String message, Throwable throwable) {
            ((java.util.logging.Logger) lo).logp(java.util.logging.Level.CONFIG, ste.getClassName(), ste.getMethodName(), message, throwable);
        }

        @Override
        protected void trace(Object lo, StackTraceElement ste, String message, Throwable throwable) {
            ((java.util.logging.Logger) lo).logp(java.util.logging.Level.FINE, ste.getClassName(), ste.getMethodName(), message, throwable);
        }
    }

    private static class ConsoleFactory extends Factory {
        @Override
        protected Object newJournal(String className) {
            return LOG_CLASS_NAME;
        }

        @Override
        protected void log(Object lo, Lv lv, StackTraceElement ste, String message, Throwable throwable) {
            String s = String.format("[%s] [%-5s] %s: %s", OffsetDateTime.now(), lv, ste, message);
            if (lv == Lv.ERROR) System.err.println(s);
            else System.out.println(s);
            if (throwable != null) {
                if (lv == Lv.ERROR) throwable.printStackTrace(System.err);
                else throwable.printStackTrace(System.out);
            }
        }
    }

    /**
     * 日志工厂
     */
    public static abstract class Factory {
        private final Map<String, Journal> cache = new HashMap<>();
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private volatile boolean isInit = false;
        private volatile boolean isSupport = false;

        protected String name() {
            return getClass().getSimpleName();
        }

        private synchronized boolean notSupport() {
            if (isInit) return !isSupport;
            isInit = true;
            try {
                return !(isSupport = init());
            } catch (Throwable e) {
                System.err.printf("The '%s' logs are not supported due to:", name());
                e.printStackTrace(System.err);
            }
            return true;
        }

        protected boolean init() throws Throwable {
            return true;
        }

        private Journal getJournal() {
            if (notSupport()) {
                throw new IllegalArgumentException();
            }
            String className = getCallStackTraceElement().getClassName();
            Journal journal;
            try {
                lock.readLock().lock();
                journal = cache.get(className);
            } finally {
                lock.readLock().unlock();
            }
            if (journal == null) {
                try {
                    lock.writeLock().lock();
                    if (journal == null) {
                        Throwable ex = null;
                        try {
                            Object journalObject = newJournal(className);
                            if (journalObject != null) {
                                journal = new Journal(this, journalObject, className);
                            }
                        } catch (Throwable e) {
                            ex = e;
                        }
                        if (journal != null) {
                            cache.put(className, journal);
                        } else {
                            throw new Error("Error implement method 'createJournal',due to the return value being null", ex);
                        }
                    }
                } finally {
                    lock.writeLock().unlock();
                }
            }
            return journal;
        }

        protected abstract Object newJournal(String className) throws Throwable;

        protected boolean isEnabledFor(Object lo, Lv lv) throws Throwable {
            switch (lv) {
                case TRACE:
                    return this.isEnabledTrace(lo);
                case DEBUG:
                    return this.isEnabledDebug(lo);
                case INFO:
                    return this.isEnabledInfo(lo);
                case WARN:
                    return this.isEnabledWarn(lo);
                case ERROR:
                    return this.isEnabledError(lo);
                default:
                    return true;
            }
        }

        protected boolean isEnabledError(Object lo) throws Throwable {
            return true;
        }

        protected boolean isEnabledWarn(Object lo) throws Throwable {
            return true;
        }

        protected boolean isEnabledInfo(Object lo) throws Throwable {
            return true;
        }

        protected boolean isEnabledDebug(Object lo) throws Throwable {
            return true;
        }

        protected boolean isEnabledTrace(Object lo) throws Throwable {
            return true;
        }

        protected void log(Object lo, Lv lv, StackTraceElement ste, String message, Throwable throwable) throws Throwable {
            switch (lv) {
                case TRACE:
                    this.trace(lo, ste, message, throwable);
                    break;
                case DEBUG:
                    this.debug(lo, ste, message, throwable);
                    break;
                case INFO:
                    this.info(lo, ste, message, throwable);
                    break;
                case WARN:
                    this.warn(lo, ste, message, throwable);
                    break;
                case ERROR:
                    this.error(lo, ste, message, throwable);
                    break;
                default:
                    break;
            }
        }

        protected void error(Object lo, StackTraceElement ste, String message, Throwable throwable) throws Throwable {
        }

        protected void warn(Object lo, StackTraceElement ste, String message, Throwable throwable) throws Throwable {

        }

        protected void info(Object lo, StackTraceElement ste, String message, Throwable throwable) throws Throwable {

        }

        protected void debug(Object lo, StackTraceElement ste, String message, Throwable throwable) throws Throwable {

        }

        protected void trace(Object lo, StackTraceElement ste, String message, Throwable throwable) throws Throwable {

        }
    }

    /**
     * 日志实例容器
     */
    private static final class Journal {
        private final Factory factory;
        private final Object logger;
        private final String className;

        private Journal(Factory factory, Object logger, String className) {
            this.factory = factory;
            this.logger = logger;
            this.className = className;
        }

        private boolean isEnabledFor(Lv lv) {
            try {
                return factory.isEnabledFor(logger, lv);
            } catch (Throwable e) {
                e.printStackTrace(System.out);
                return false;
            }
        }

        private void printLog(Lv lv, String messageTemplate, Object... args) {
            StackTraceElement ste = getCallStackTraceElement();
            Throwable throwable = null;
            if (args != null && args.length > 0) {
                if (args[args.length - 1] instanceof Throwable) {
                    throwable = (Throwable) args[args.length - 1];
                    args = Arrays.copyOf(args, args.length - 1);
                }
            } else if (args == null) {
                args = new Object[0];
            }
            try {
                String message = messageTemplate;
                if (args.length != 0) {
                    message = String.format(messageTemplate, args);
                } else if (message == null) {
                    message = "";
                }
                factory.log(logger, lv, ste, message, throwable);
            } catch (Throwable e) {
                e.printStackTrace(System.out);
            }
        }

        @Override
        public String toString() {
            return "Logger: " + className;
        }
    }

    /**
     * 日志等级
     */
    public enum Lv {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}
