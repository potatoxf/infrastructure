package potatoxf.infrastructure.value;

import java.time.*;
import java.util.Date;

/**
 * 时间值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
class WhenValImpl implements WhenVal {
    private static final int[] MONTH_DAYS = new int[]{59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
    protected volatile String zone;
    private volatile long epochMilli;
    private volatile long epochSecond;
    private volatile long nanoOfSecond;
    private volatile int nanos;
    private volatile int micro;
    private volatile int milli;
    private volatile int second;
    private volatile int minute;
    private volatile int hour;
    private volatile int day;
    private volatile int month;
    private volatile int year;

    protected WhenValImpl(long epochMilli, String zone) {
        initEpochMilli(epochMilli, zone);
        init();
    }

    protected WhenValImpl(long epochSecond, long nanoOfSecond, String zone) {
        initEpochSecond(epochSecond, nanoOfSecond, zone);
        init();
    }

    @Override
    public String atZone() {
        return zone;
    }

    @Override
    public long atEpochMilli() {
        return epochMilli;
    }

    @Override
    public long atEpochSecond() {
        return epochSecond;
    }

    @Override
    public long atNanoOfSecond() {
        return nanoOfSecond;
    }

    @Override
    public int year() {
        return year;
    }

    @Override
    public int month() {
        return month;
    }

    @Override
    public int day() {
        return day;
    }

    @Override
    public int hour() {
        return hour;
    }

    @Override
    public int minute() {
        return minute;
    }

    @Override
    public int second() {
        return second;
    }

    @Override
    public int milli() {
        return milli;
    }

    @Override
    public int micro() {
        return micro;
    }

    @Override
    public int nano() {
        return nanos;
    }

    @Override
    public Date toDate() {
        return new Date(atEpochMilli());
    }

    @Override
    public Instant toInstant() {
        return Instant.ofEpochSecond(epochSecond, nanoOfSecond);
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofEpochSecond(epochSecond, (int) nanoOfSecond, ZoneOffset.of(zone));
    }

    @Override
    public LocalDate toLocalDate() {
        return this.toLocalDateTime().toLocalDate();
    }

    @Override
    public LocalTime toLocalTime() {
        return this.toLocalDateTime().toLocalTime();
    }

    @Override
    public OffsetDateTime toOffsetDateTime() {
        return OffsetDateTime.of(this.toLocalDateTime(), ZoneOffset.of(zone));
    }

    @Override
    public OffsetTime toOffsetTime() {
        return this.toOffsetDateTime().toOffsetTime();
    }

    @Override
    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.of(this.toLocalDateTime(), ZoneOffset.of(zone));
    }

    protected final void initEpochMilli(long epochMilli, String zone) {
        this.zone = zone;
        this.epochMilli = epochMilli;
        this.epochSecond = Math.floorDiv(epochMilli, 1_000);
        this.nanoOfSecond = Math.floorMod(epochMilli, 1_000) * 1_000_000;
    }

    protected final void initEpochSecond(long epochSecond, long nanoOfSecond, String zone) {
        this.zone = zone;
        long numberOfSecond = Math.floorDiv(nanoOfSecond, 1_000_000_000);
        try {
            epochSecond = Math.addExact(epochSecond, numberOfSecond);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Beyond the nanosecond plus to the number of seconds out of the range long：" + epochSecond + " + " + numberOfSecond + " > Long.MAX_VALUE(" + Long.MAX_VALUE + ")", e);
        }
        nanoOfSecond = Math.floorMod(nanoOfSecond, 1_000_000_000);
        long epochMilli;
        try {
            epochMilli = Math.multiplyExact(epochSecond, 1_000);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("The number of seconds to convert milliseconds is outside the range long：" + epochSecond + " * " + 1000 + " > Long.MAX_VALUE(" + Long.MAX_VALUE + ")", e);
        }
        epochMilli = epochMilli + Math.floorDiv(nanoOfSecond, 1_000_000);
        this.epochMilli = epochMilli;
        this.epochSecond = epochSecond;
        this.nanoOfSecond = nanoOfSecond;
    }

    protected void init() {
        final long totalSecond = epochSecond + WhenVal.parseZoneOffsetSecond(zone);
        final long totalMinute = Math.floorDiv(totalSecond, 60);
        final long totalHour = Math.floorDiv(totalMinute, 60);
        final long totalDay = Math.floorDiv(totalHour, 24);
        nanos = (int) Math.floorMod(nanoOfSecond, 1_000);
        micro = (int) Math.floorMod(nanoOfSecond / 1_000, 1_000);
        milli = (int) Math.floorDiv(nanoOfSecond, 1_000_000);
        second = (int) (totalSecond - totalMinute * 60);// 秒
        minute = (int) (totalMinute - totalHour * 60);// 分
        hour = (int) (totalHour - totalDay * 24);// 时
        int sy = 1970;
        int year = (int) (sy + totalDay / 366);
        int diffDays;
        boolean leapYear;
        while (true) {
            int diff = (year - sy) * 365;
            diff += (year - 1) / 4 - (sy - 1) / 4;
            diff -= ((year - 1) / 100 - (sy - 1) / 100);
            diff += (year - 1) / 400 - (sy - 1) / 400;
            diffDays = (int) (totalDay - diff);
            leapYear = (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
            if (!leapYear && diffDays < 365 || leapYear && diffDays < 366) {
                break;
            } else {
                year++;
            }
        }
        this.year = year;
        if (diffDays < 31) {
            this.month = 1;
            this.day = diffDays < 0 ? 1 : diffDays + 1;
            return;
        }
        for (int i = MONTH_DAYS.length - 1; i >= 0; i--) {
            if (diffDays >= MONTH_DAYS[i] + (leapYear ? 1 : 0)) {
                this.month = i + 3;
                this.day = diffDays - MONTH_DAYS[i] + 1;
                return;
            }
        }
        this.month = 2;
        this.day = diffDays - 31 + 1;
    }

    @Override
    public String toString() {
        return this.display();
    }
}
