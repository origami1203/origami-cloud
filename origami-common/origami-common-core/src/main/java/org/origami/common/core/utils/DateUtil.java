package org.origami.common.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author origami
 * @version 1.0.0
 * @date 2021-12-30 12:20
 */
public abstract class DateUtil {

    private static final String DEFAULT_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime
     * @return 若localDataTime为null，返回null
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param date Date
     * @return 若date为null，则返回null
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param date Date
     * @return 以yyyy-MM-dd HH:mm:ss格式化date
     */
    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT_STR);
    }

    /**
     * @param date   Date
     * @param format 格式化字符串
     * @return 如果date是null, 返回null
     */
    public static String format(Date date, String format) {
        if (Objects.isNull(date)) {
            return null;
        }
        Assert.nonNull(format, "formatter不能为空");

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    /**
     * 将指定的字符串以指定格式解析
     *
     * @param dateStr 时间字符串
     * @param format  格式化字符串
     * @return 无法解析会抛出异常
     */
    public static Date parse(String dateStr, String format) {
        Assert.nonNull(format, "formatter不能为空");
        Assert.nonNull(dateStr, "dateStr不能为空");

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new org.origami.common.core.exception.ParseException("日期解析错误");
        }
        return date;
    }

    /**
     * 根据指定数据生成date
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       时
     * @param minute     分
     * @param second     秒
     * @return date
     */
    public static Date of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute,
                second);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @return 时分秒为0
     */
    public static Date of(int year, int month, int dayOfMonth) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
        return localDateTimeToDate(localDateTime);
    }

}
