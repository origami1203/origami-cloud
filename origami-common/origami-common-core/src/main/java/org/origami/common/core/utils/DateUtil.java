package org.origami.common.core.utils;

import cn.hutool.core.lang.Assert;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

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
@UtilityClass
public class DateUtil {
    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public Date localDateTime2Date(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param date Date
     * @return LocalDateTime
     */
    public LocalDateTime date2LocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param date   Date
     * @param format 格式化字符串
     * @return 如果date是null, 返回null
     */
    public String format(Date date, String format) {
        if (Objects.isNull(date)) {
            return null;
        }
        Assert.notNull(format, "formatter不能为空");

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
    @SneakyThrows
    public Date parse(String dateStr, String format) {
        Assert.notNull(format, "formatter不能为空");
        Assert.notNull(dateStr, "dateStr不能为空");

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.parse(dateStr);
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
    public Date of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        return localDateTime2Date(localDateTime);
    }

    /**
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @return 时分秒默认为null
     */
    public Date of(int year, int month, int dayOfMonth) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
        return localDateTime2Date(localDateTime);
    }

}
