package potatoxf.infrastructure.value;

import potatoxf.api.support.Arg;

import java.time.*;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * {@link Long}类型值
 * <p/>
 * Create Time:2024-04-08
 *
 * @author potatoxf
 */
public interface WhenVal extends LongVal {
    /**
     * 时区格式
     */
    Pattern ZONE_FORMAT = Pattern.compile("^((([-+](0[0-9]|1[0-2]))|\\+1[34])(:[0-5][0-9])?)$");

    /**
     * 默认时区
     *
     * @return 返回默认时区
     */
    static String defaultZone() {
        return WhenVal.parseZoneFormat(ZoneId.systemDefault());
    }

    /**
     * 解析时区偏移秒数
     *
     * @param zone 时区
     * @return 返回时区偏移秒数
     */
    static int parseZoneOffsetSecond(String zone) {
        if (!WhenVal.isCorrectZoneFormat(zone)) {
            throw new IllegalArgumentException("The time zone ‘" + zone + "’is not formatted correctly");
        }
        char[] cs = zone.toCharArray();
        int symbol = cs[0] == '-' ? -1 : 1;
        int result = 3600 * symbol * ((cs[1] - '0') * 10 + (cs[2] - '0'));
        if (cs.length > 3) {
            result += 60 * symbol * ((cs[4] - '0') * 10 + (cs[5] - '0'));
        }
        return result;
    }

    /**
     * 解析时区格式
     *
     * @param zone 时区
     * @return 返回时区格式
     */
    static String parseZoneFormat(Object zone) {
        if (zone instanceof String) return (String) zone;
        ZoneId zoneId = WhenVal.parseZoneId(zone);
        if (zoneId instanceof ZoneOffset) {
            return zoneId.toString().substring(0, 6);
        } else {
            return zoneId.getRules().getOffset(Instant.now()).toString().substring(0, 6);
        }
    }

    /**
     * 解析时区
     *
     * @param zone 时区
     * @return 返回时区
     */
    static ZoneId parseZoneId(Object zone) {
        return Arg.effectiveZoneId(zone);
    }

    /**
     * 是否是正确时区格式
     *
     * @param zone 时区
     * @return 如果正确返回true，否则返回false
     */
    static boolean isCorrectZoneFormat(String zone) {
        return zone != null && ZONE_FORMAT.matcher(zone).matches();
    }

    static WhenVal now() {
        return new WhenValImpl(System.currentTimeMillis(), WhenVal.defaultZone());
    }

    static WhenVal of(long epochMilli) {
        return new WhenValImpl(epochMilli, WhenVal.defaultZone());
    }

    static WhenVal of(long epochMilli, Object zone) {
        return new WhenValImpl(epochMilli, WhenVal.parseZoneFormat(zone));
    }

    static WhenVal of(long epochSecond, long nanoOfSecond) {
        return new WhenValImpl(epochSecond, nanoOfSecond, WhenVal.defaultZone());
    }

    static WhenVal of(long epochSecond, long nanoOfSecond, Object zone) {
        return new WhenValImpl(epochSecond, nanoOfSecond, WhenVal.parseZoneFormat(zone));
    }

    static WhenVal of(LocalDateTime input, Object zone) {
        return WhenVal.of(input.atZone(WhenVal.parseZoneId(zone)));
    }

    static WhenVal of(OffsetDateTime input) {
        return new WhenValImpl(input.toEpochSecond(), input.getNano(), WhenVal.parseZoneFormat(input.getOffset()));
    }

    static WhenVal of(ZonedDateTime input) {
        return new WhenValImpl(input.toEpochSecond(), input.getNano(), WhenVal.parseZoneFormat(input.getZone()));
    }

    /**
     * 在哪个时区
     *
     * @return 返回时区
     */
    String atZone();

    /**
     * 在哪个时区
     *
     * @return 返回时区
     */
    default ZoneOffset atZoneOffset() {
        return ZoneOffset.of(this.atZone());
    }

    /**
     * 在哪个时区
     *
     * @return 返回时区
     */
    default ZoneId atZoneId() {
        return ZoneId.of(this.atZone());
    }

    /**
     * 获取从 1970-01-01T00:00:00Z 开始的毫米秒数{@link #atEpochMilli()}
     *
     * @return 返回毫秒数
     */
    @Override
    default Long getValue() {
        return this.atEpochMilli();
    }

    /**
     * 从 1970-01-01T00:00:00Z 开始的毫米秒数
     *
     * @return 返回毫秒数
     */
    long atEpochMilli();

    /**
     * 从 1970-01-01T00:00:00Z 开始的秒数
     *
     * @return 返回秒数
     */
    long atEpochSecond();

    /**
     * 从 1970-01-01T00:00:00Z 开始的秒数之纳秒数的部分
     *
     * @return 返回纳秒数
     */
    long atNanoOfSecond();

    /**
     * 获取年份
     *
     * @return 返回年份
     */
    int year();

    /**
     * 获取月份
     *
     * @return 返回月份
     */
    int month();

    /**
     * 获取日期
     *
     * @return 返回日期
     */
    int day();

    /**
     * 获取小时
     *
     * @return 返回小时
     */
    int hour();

    /**
     * 获取分钟
     *
     * @return 返回分钟
     */
    int minute();

    /**
     * 获取秒
     *
     * @return 返回秒
     */
    int second();

    /**
     * 获取毫秒
     *
     * @return 返回毫秒
     */
    int milli();

    /**
     * 获取微妙
     *
     * @return 返回微妙
     */
    int micro();

    /**
     * 获取纳秒
     *
     * @return 返回纳秒
     */
    int nano();

    /**
     * 年月日值
     *
     * @return 返回年月日值
     */
    default int ymdValue() {
        return year() * 10000 + month() * 100 + day();
    }

    /**
     * 年月日显示
     *
     * @return 返回年月日显示
     */
    default String ymdDisplay() {
        return ymdDisplay("-");
    }

    /**
     * 年月日显示
     *
     * @param split 分割符号
     * @return 返回年月日显示
     */
    default String ymdDisplay(String split) {
        if (split == null) return String.valueOf(ymdValue());
        StringBuilder sb = new StringBuilder(8 + split.length() * 2);
        for (int v = year(), i = 1000; i != 0; v %= i, i /= 10) sb.append(v / i);
        sb.append(split);
        for (int v = month(), i = 10; i != 0; v %= i, i /= 10) sb.append(v / i);
        sb.append(split);
        for (int v = day(), i = 10; i != 0; v %= i, i /= 10) sb.append(v / i);
        return sb.toString();
    }

    /**
     * 时分秒值
     *
     * @return 返回时分秒值
     */
    default int hmsValue() {
        return year() * 10000 + month() * 100 + day();
    }

    /**
     * 时分秒显示
     *
     * @return 返回时分秒显示
     */
    default String hmsDisplay() {
        return hmsDisplay(":");
    }

    /**
     * 时分秒显示
     *
     * @param split 分割符号
     * @return 返回时分秒显示
     */
    default String hmsDisplay(String split) {
        if (split == null) return String.valueOf(hmsValue());
        StringBuilder sb = new StringBuilder(6 + split.length() * 2);
        for (int v = hour(), i = 10; i != 0; v %= i, i /= 10) sb.append(v / i);
        sb.append(split);
        for (int v = minute(), i = 10; i != 0; v %= i, i /= 10) sb.append(v / i);
        sb.append(split);
        for (int v = second(), i = 10; i != 0; v %= i, i /= 10) sb.append(v / i);
        return sb.toString();
    }

    /**
     * 年月日时分秒显示
     *
     * @return 返回年月日时分秒显示
     */
    default String display() {
        return this.display("-", " ", ":");
    }

    /**
     * 年月日时分秒显示
     *
     * @param ymdSplit 年月日分割符号
     * @param hmsSplit 时分秒分割符号
     * @return 返回年月日时分秒显示
     */
    default String display(String ymdSplit, String hmsSplit) {
        return this.display(ymdSplit, " ", hmsSplit);
    }

    /**
     * 年月日时分秒显示
     *
     * @param ymdSplit    年月日分割符号
     * @param centerSplit 中间割符号
     * @param hmsSplit    时分秒分割符号
     * @return 返回年月日时分秒显示
     */
    default String display(String ymdSplit, String centerSplit, String hmsSplit) {
        if (centerSplit == null) centerSplit = "";
        return ymdDisplay(ymdSplit) + centerSplit + hmsDisplay(hmsSplit);
    }

    /**
     * 获取{@link Date}实例
     *
     * @return 返回 {@link Date}实例
     */
    Date toDate();

    /**
     * 获取{@link Instant}实例
     *
     * @return 返回 {@link Instant}实例
     */
    Instant toInstant();

    /**
     * 获取{@link LocalDateTime}实例
     *
     * @return 返回 {@link LocalDateTime}实例
     */
    LocalDateTime toLocalDateTime();

    /**
     * 获取{@link LocalDate}实例
     *
     * @return 返回 {@link LocalDate}实例
     */
    LocalDate toLocalDate();

    /**
     * 获取{@link LocalTime}实例
     *
     * @return 返回 {@link LocalTime}实例
     */
    LocalTime toLocalTime();

    /**
     * 获取{@link OffsetDateTime}实例
     *
     * @return 返回 {@link OffsetDateTime}实例
     */
    OffsetDateTime toOffsetDateTime();

    /**
     * 获取{@link OffsetTime}实例
     *
     * @return 返回 {@link OffsetTime}实例
     */
    OffsetTime toOffsetTime();

    /**
     * 获取{@link ZonedDateTime}实例
     *
     * @return 返回 {@link ZonedDateTime}实例
     */
    ZonedDateTime toZonedDateTime();
}
