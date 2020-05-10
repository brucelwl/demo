package com.example.demo.tools;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Created by bruce on 2020-05-10.
 */
public class DateUtils {

    private static ZoneId zone = ZoneId.systemDefault();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //非线程安全
    private static SimpleDateFormat sdfThreadUnSafe = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static int compareTo(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    /**
     * date 转 时间字符串,格式: yyyy-MM-dd HH:mm:ss
     */
    public static String toString(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return dtf.format(localDateTime);
    }

    //日期格式化
    public static LocalDateTime parseDate(String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, dtf);
        return localDateTime;
    }

    //日期解析
    public static Date parseStr2Date(String time) {
        if (StringUtils.hasText(time)) {
            try {
                return ((SimpleDateFormat) sdfThreadUnSafe.clone()).parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //日期格式化
    public static String formatDate(Date date) {
        if (Objects.isNull(date)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


    /**
     * 字符串时间yyyy-MM-dd HH:mm:ss 转 Date
     */
    public static Date toDate(String time) {
        return Date.from(Instant.from(LocalDateTime.parse(time, dtf).atZone(ZoneId.systemDefault())));
    }

    /**
     * 判断时间 endDate - startDate 是否在指定的天数内 <br/>
     * 时间格式: yyyy-MM-dd HH:mm:ss
     */
    public static boolean inInterval(String startDate, String endDate, int intervalDays) {
        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            return false;
        }
        long ss = (long) intervalDays * 60 * 60 * 24; //s
        long s = (toDate(endDate).getTime() / 1000 - toDate(startDate).getTime() / 1000);
        return s > 0 && s <= ss;
    }

    public static boolean notInInterval(String startDate, String endDate, int intervalDays) {
        return !inInterval(startDate, endDate, intervalDays);
    }


}
