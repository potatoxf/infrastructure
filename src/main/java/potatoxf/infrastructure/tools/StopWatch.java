package potatoxf.infrastructure.tools;

import lombok.Getter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 简单的秒表，允许对许多任务进行计时，每个命名任务的运行时间和运行时间。
 * 请注意，此对象不是为线程安全而设计的，因此不会使用同步。
 * <p/>
 * Create Time:2024-04-15
 *
 * @author potatoxf
 */
public final class StopWatch {
    /**
     * {@code StopWatch} 的标识符,当我们有多个秒表的输出并且需要在日志或控制台输出中区分它们。
     */
    @Getter
    private final String id;
    /**
     * 任务信息
     */
    private final TaskInfo[] taskInfos;
    /**
     * 任务索引
     */
    private int taskLastIndex = -1;
    /**
     * 当前任务的开始时间
     */
    private long startTimeNanos;
    /**
     * 当前任务的名称
     */
    @Getter
    private String currentTaskName;
    /**
     * 获取定时的任务数。
     */
    @Getter
    private int taskCount;
    /**
     * 总运行时间。
     */
    @Getter
    private double totalTimeNanos;

    public StopWatch() {
        this("", 10);
    }

    /**
     * @param id        此秒表的标识符
     * @param taskLimit 任务最大任务限制
     */
    public StopWatch(String id, int taskLimit) {
        this.id = id;
        this.taskInfos = new TaskInfo[Math.max(taskLimit, 10)];
    }

    /**
     * Start an unnamed task.
     * <p>The results are undefined if {@link #stop()} or timing methods are
     * called without invoking this method first.
     *
     * @see #start(String)
     * @see #stop()
     */
    public void start() throws IllegalStateException {
        this.start("");
    }

    /**
     * Start a named task.
     * <p>The results are undefined if {@link #stop()} or timing methods are
     * called without invoking this method first.
     *
     * @param taskName the name of the task to start
     * @see #start()
     * @see #stop()
     */
    public void start(String taskName) throws IllegalStateException {
        if (this.currentTaskName != null) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        }
        this.currentTaskName = taskName;
        this.startTimeNanos = System.nanoTime();
    }

    /**
     * Stop the current task.
     * <p>The results are undefined if timing methods are called without invoking
     * at least one pair of {@code start()} / {@code stop()} methods.
     *
     * @see #start()
     * @see #start(String)
     */
    public void stop() throws IllegalStateException {
        if (this.currentTaskName == null) {
            throw new IllegalStateException("Can't stop StopWatch: it's not running");
        }
        long lastTime = System.nanoTime() - this.startTimeNanos;
        this.totalTimeNanos += lastTime;
        taskInfos[++taskLastIndex % taskInfos.length] = new TaskInfo(this.currentTaskName, lastTime);
        ++taskCount;
        currentTaskName = null;
    }

    /**
     * Generate a table describing all tasks performed in seconds
     * (with decimal points in nanosecond precision).
     * <p>For custom reporting, call {@link #getTaskInfos()} and use the data directly.
     */
    public String prettyPrint() {
        return this.prettyPrint(TimeUnit.SECONDS);
    }

    /**
     * Generate a table describing all tasks performed in the requested time unit
     * (with decimal points in nanosecond precision).
     * <p>For custom reporting, call {@link #getTaskInfos()} and use the data directly.
     *
     * @param timeUnit the unit to use for rendering total time and task time
     * @see #prettyPrint()
     * @see #getTotalTime(TimeUnit)
     * @see TaskInfo#getTime(TimeUnit)
     */
    public String prettyPrint(TimeUnit timeUnit) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(9);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance(Locale.ENGLISH);
        pf.setMinimumIntegerDigits(2);
        pf.setGroupingUsed(false);
        StringBuilder sb = new StringBuilder(128);
        sb.append("StopWatch '").append(getId()).append("': ");
        String total = (timeUnit == TimeUnit.NANOSECONDS ?
                nf.format(getTotalTimeNanos()) : nf.format(getTotalTime(timeUnit)));
        sb.append(total).append(" ").append(timeUnit.name().toLowerCase(Locale.ENGLISH));
        int width = Math.max(sb.length(), 40);
        sb.append("\n");
        if (this.taskLastIndex != -1) {
            String unitName = timeUnit.name();
            unitName = unitName.charAt(0) + unitName.substring(1).toLowerCase(Locale.ENGLISH);
            unitName = String.format("%-12s", unitName);
            for (int i = 0; i < width; i++) sb.append("-");
            sb.append("\n");
            sb.append(unitName).append("  %       Task name\n");
            for (int i = 0; i < width; i++) sb.append("-");
            sb.append("\n");
            int digits = total.indexOf('.');
            if (digits < 0) {
                digits = total.length();
            }
            nf.setMinimumIntegerDigits(digits);
            nf.setMaximumFractionDigits(10 - digits);
            int len = Math.min(taskLastIndex + 1, taskInfos.length), si = taskLastIndex >= taskInfos.length ? taskLastIndex % taskInfos.length + 1 : 0;
            for (int i = 0; i < len; i++) {
                TaskInfo task = taskInfos[(si + i) % taskInfos.length];
                sb.append(String.format("%-14s", (timeUnit == TimeUnit.NANOSECONDS ?
                        nf.format(task.getTimeNanos()) : nf.format(task.getTime(timeUnit)))));
                sb.append(String.format("%-8s",
                        pf.format(task.getTimeNanos() / getTotalTimeNanos())));
                sb.append(task.getTaskName()).append('\n');
            }
        } else {
            sb.append("No task info kept");
        }
        return sb.toString();
    }

    /**
     * Get a short description of the total running time in seconds.
     *
     * @see #prettyPrint()
     * @see #prettyPrint(TimeUnit)
     */
    public String shortSummary() {
        return "StopWatch '" + this.getId() + "': " + this.getTotalTime(TimeUnit.SECONDS) + " seconds";
    }

    /**
     * Generate an informative string describing all tasks performed in seconds.
     *
     * @see #prettyPrint()
     * @see #prettyPrint(TimeUnit)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(shortSummary());
        if (taskInfos != null) {
            int len = Math.min(taskLastIndex + 1, taskInfos.length), si = taskLastIndex >= taskInfos.length ? taskLastIndex % taskInfos.length + 1 : 0;
            for (int i = 0; i < len; i++) {
                TaskInfo task = taskInfos[(si + i) % taskInfos.length];
                sb.append("; [").append(task.getTaskName()).append("] took ").append(task.getTime(TimeUnit.SECONDS)).append(" seconds");
                long percent = Math.round(100.0 * task.getTimeNanos() / getTotalTimeNanos());
                sb.append(" = ").append(percent).append('%');
            }
        } else {
            sb.append("; no task info kept");
        }
        return sb.toString();
    }

    /**
     * Determine whether this {@code StopWatch} is currently running.
     */
    public boolean isRunning() {
        return currentTaskName != null;
    }

    /**
     * Get the last task as a {@link TaskInfo} object.
     */
    public TaskInfo getLastTaskInfo() {
        return taskLastIndex == -1 ? null : taskInfos[taskLastIndex % taskInfos.length];
    }

    /**
     * Get an array of the data for tasks performed.
     */
    public TaskInfo[] getTaskInfos() {
        int len = Math.min(taskLastIndex + 1, taskInfos.length), si = taskLastIndex >= taskInfos.length ? taskLastIndex % taskInfos.length + 1 : 0;
        TaskInfo[] result = new TaskInfo[len];
        for (int i = 0; i < len; i++) {
            result[i] = taskInfos[(si + i) % taskInfos.length];
        }
        return result;
    }

    /**
     * Get the total time for all tasks in the requested time unit
     * (with decimal points in nanosecond precision).
     *
     * @param timeUnit the unit to use
     * @see #getTotalTimeNanos()
     */
    public double getTotalTime(TimeUnit timeUnit) {
        return totalTimeNanos / TimeUnit.NANOSECONDS.convert(1, timeUnit);
    }

    /**
     * Nested class to hold data about one task executed within the {@code StopWatch}.
     */
    @Getter
    public static final class TaskInfo {
        /**
         * Get the name of this task.
         */
        private final String taskName;
        /**
         * Get the time this task took in nanoseconds.
         */
        private final double timeNanos;

        TaskInfo(String taskName, double timeNanos) {
            this.taskName = taskName;
            this.timeNanos = timeNanos;
        }

        /**
         * Get the time this task took in the requested time unit
         * (with decimal points in nanosecond precision).
         *
         * @param timeUnit the unit to use
         */
        public double getTime(TimeUnit timeUnit) {
            return timeNanos / TimeUnit.NANOSECONDS.convert(1, timeUnit);
        }
    }
}
