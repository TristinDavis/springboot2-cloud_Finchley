package com.aha.tech.base.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);


    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_COMPACT = "yyyyMMdd";
    public static final String DATE_FORMAT_COMPACTFULL = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_FULL_MSEL = "yyyyMMddHHmmssSSSS";
    public static final String DATE_YEAR_MONTH = "yyyyMM";
    public static final String DATE_FORMAT_FULL_MSE = "yyyyMMddHHmmssSSS";
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    /**
     * 返回当前时间,格式化 : yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String currentDateByDefaultFormat() {
        LocalDateTime now = LocalDateTime.now(DEFAULT_TIME_ZONE.toZoneId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_FULL);
        String dataTime = now.format(dateTimeFormatter);
        return dataTime;
    }

    /**
     * 返回当前时间
     *
     * @param format
     * @return
     */
    public static String currentDateByFormat(String format) {
        LocalDateTime now = LocalDateTime.now(DEFAULT_TIME_ZONE.toZoneId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        String dataTime = now.format(dateTimeFormatter);
        return dataTime;
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    public static String dateByDefaultFormat(Date date) {
        return getDate(date, null);
    }

    /**
     * 返回当前时间
     *
     * @param format
     * @return
     */
    public static String dateByFormat(Date date, String format) {
        return getDate(date, format);
    }

    /**
     * instant类型转date
     *
     * @return
     */
    public static Date instant2Date(Instant instant) {
        return Date.from(instant);
    }

    /**
     * localDate类型转date
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZonedDateTime zdt = localDate.atStartOfDay(DEFAULT_TIME_ZONE.toZoneId());
        return Date.from(zdt.toInstant());
    }

    /**
     * localDateTime转date
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(DEFAULT_TIME_ZONE.toZoneId());
        return Date.from(zdt.toInstant());
    }


    /**
     * 返回指定格式化字符串
     * @param date
     * @param format
     * @return
     */
    private static String getDate(Date date, String format) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(DEFAULT_TIME_ZONE.toZoneId()).toLocalDateTime();
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_FULL;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        String dataTime = localDateTime.format(dateTimeFormatter);
        return dataTime;
    }

    /**
     * 自定义年月日
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static LocalDate customizeDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    /**
     * 获取自定义时分秒
     *
     * @return
     */
    public static LocalTime customizeTime(int hour, int minute, int second) {
        return LocalTime.of(hour, minute, second);
    }

    /**
     * 获取自定义年月日时分秒
     *
     * @return
     */
    public static LocalDateTime customizeDateTime(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * 当前日期时间戳(yyyyMMddHHmmssSSSS)
     *
     * @return
     * @author liangxuekais
     */
    public static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now(DEFAULT_TIME_ZONE.toZoneId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_FULL_MSEL);
        return now.format(dateTimeFormatter);
    }

    /**
     * 返回当前localDate
     * @return
     */
    public static LocalDate currentlocalDate() {
        return LocalDate.now(DEFAULT_TIME_ZONE.toZoneId());
    }

    /**
     * 返回当前localDateTime
     * @return
     */
    public static LocalDateTime currentlocalDateTime() {
        return LocalDateTime.now(DEFAULT_TIME_ZONE.toZoneId());
    }

    /**
     * 返回当前时间 iso 8601格式
     * @return
     */
    public static Instant currentInstatn() {
        return Instant.now(Clock.system(DEFAULT_TIME_ZONE.toZoneId()));
    }

    /**
     * date2String
     * 默认 yyyy-MM-dd HH:mm:ss
     * @return 当前日期的标准形式字符串
     */
    public static String convertDate2Str(String format, Date d) {
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_FULL;
        }
        return new SimpleDateFormat(format).format(d);
    }

    /**
     * LocalDate转字符串
     * @param format
     * @param localDate
     * @return
     */
    public static String convertDate2Str(String format, LocalDate localDate) {
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_FULL;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalTime localTime = LocalTime.now(DEFAULT_TIME_ZONE.toZoneId());
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * LocalTime转字符串
     * @param format
     * @param localTime
     * @return
     */
    public static String convertDate2Str(String format, LocalTime localTime) {
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_FULL;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.now(DEFAULT_TIME_ZONE.toZoneId());
        return LocalDateTime.of(localDate, localTime).format(dateTimeFormatter);
    }

    /**
     * LocalDateTime转字符串
     * @param format
     * @param localDateTime
     * @return
     */
    public static String convertDate2Str(String format, LocalDateTime localDateTime) {
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_FULL;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 将特定格式的日期转换为Date对象
     * @param dateString 特定格式的日期
     * @param format 格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date convertStr2Date(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dateString);
        } catch (ParseException e) {
            logger.error("Parse " + dateString + " with format " + format + " error!", e);
        }
        return null;
    }
}
