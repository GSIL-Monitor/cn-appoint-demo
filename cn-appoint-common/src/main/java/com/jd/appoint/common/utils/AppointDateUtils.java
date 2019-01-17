package com.jd.appoint.common.utils;


import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 日期帮助类
 * Created by luqiang3 on 2018/5/10.
 */
public class AppointDateUtils extends DateUtils {

    /**
     * 检测字符串是否为日期
     *
     * @param dateTime 时间字符串
     * @param pattern  Eg "yyyy-mm-dd"
     * @return 返回结果
     */
    public static boolean isDateTime(String dateTime, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date dt = df.parse(dateTime, pos);
        return !(dt == null);
    }

    /**
     * 取得某日期时间的特定表示格式的字符串
     *
     * @param format 时间格式
     * @param date   某日期（Date）
     * @return 某日期的字符串
     */
    public static synchronized String getDate2Str(String format, Date date) {
        LocalDateTime localDateTime = getLocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获得动态的日期分钟
     * 获得未来多少分钟后的某个时间
     *
     * @param format
     * @param date
     * @return
     */
    public static String getAutoMinDate(String format, Date date, int min) {
        LocalDateTime localDateTime = getLocalDateTime(addMinutes(date, min));
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }


    /**
     * 将特定格式的时间字符串转化为Date类型
     *
     * @param format 时间格式
     * @param str    某日期的字符串
     * @return 某日期（Date）
     */
    public static synchronized Date getStrToDate(String format, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, new Locale("id", "ID"));
        ParsePosition parseposition = new ParsePosition(0);
        return simpleDateFormat.parse(str, parseposition);
    }

    /**
     * 对数据进行GMT转换
     * @param format
     * @param date
     * @return
     */
    public static synchronized String getIdGMTTime(String format, Date date) {
        if(null == date){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, new Locale("id", "ID"));
        return simpleDateFormat.format(date);
    }

    /**
     * 两个日期的天数
     *
     * @param date1 前日期
     * @param date2 后日期
     * @return
     */
    public static int daysBetweenTwoDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("date null");
        }
        Instant instant = date1.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate d1 = LocalDateTime.ofInstant(instant, zone).toLocalDate();
        Instant instant2 = date2.toInstant();
        LocalDate d2 = LocalDateTime.ofInstant(instant2, zone).toLocalDate();
        return (int) d1.until(d2, ChronoUnit.DAYS);
    }


    /**
     * 两个日期的年数
     *
     * @param date1 前日期
     * @param date2 后日期
     * @return
     */
    public static int daysBetweenTwoYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("date null");
        }
        Instant instant = date1.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate d1 = LocalDateTime.ofInstant(instant, zone).toLocalDate();
        Instant instant2 = date2.toInstant();
        LocalDate d2 = LocalDateTime.ofInstant(instant2, zone).toLocalDate();
        return (int) d1.until(d2, ChronoUnit.YEARS);
    }


    /**
     * 返回某一天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        if (date == null) {
            throw new RuntimeException("date null");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return Date.from(calendar.toInstant());
    }

    /**
     * 返回某一天的截止时间
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        if (date == null) {
            throw new RuntimeException("date null");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return Date.from(calendar.toInstant());
    }

    /**
     * 获得当前时间的LocalDateTime
     *
     * @param date
     * @return
     */
    private static LocalDateTime getLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将小时转换为分钟，格式：hh:mm；h:mm
     * @param hhmm
     * @return
     */
    public static Integer hour2Minute(String hhmm){
        if(null == hhmm || "".equals(hhmm)){
            return null;
        }
        if(!Pattern.compile("[0-2]?\\d[ :-][0-5]\\d").matcher(hhmm).matches()){
            throw new RuntimeException("hour2Minutes format is error.");
        }
        int hour = Integer.parseInt(hhmm.substring(0, hhmm.indexOf(":")));
        int minute = Integer.parseInt(hhmm.substring(hhmm.indexOf(":")+1, hhmm.length()));
        return hour * 60 + minute;
    }

    /**
     * 将分钟转换为小时
     * @param minute
     * @return 格式 hh:mm
     */
    public static String minute2Hour(Integer minute){
        if(null == minute){
            return null;
        }
        Integer hour = minute / 60;//获得小时数
        Integer minuteTemp = minute - hour * 60;//获得分钟数
        StringBuilder builder = new StringBuilder();
        if(hour.toString().length() < 2){//小时数不足两位前面补0
            builder.append("0");
        }
        builder.append(hour).append(":");
        if(minuteTemp.toString().length() < 2){//分钟数不足两位前面补0
            builder.append("0");
        }
        builder.append(minuteTemp);
        return builder.toString();
    }

    /**
     * 获得指定日期对应的星期数
     * @param day
     * @return 1,2,3,4,5,6,7
     */
    public static int day2Week(Date day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if(week == 1){
            return 7;
        }
        return week - 1;
    }

    /**
     * 获得指定日期对应的星期数
     * @param day 格式：yyyy-MM-dd
     * @return 1,2,3,4,5,6,7
     */
    public static int day2Week(String day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStrToDate("yyyy-MM-dd", day));
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if(week == 1){
            return 7;
        }
        return week - 1;
    }

    /**
     * 获得指定时间当前的秒数
     * @param date
     * @return
     */
    public static int getSeconds(Date date){
        String hms = getDate2Str("HH:mm:ss", date);
        int hour = Integer.parseInt(hms.substring(0, 2));
        int minute = Integer.parseInt(hms.substring(3, 5));
        int second = Integer.parseInt(hms.substring(6, 8));
        return hour * 60 * 60 + minute * 60 + second;
    }

    /**
     * 日期是否相等
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isSameDate(Date startDate,Date endDate){
        if(0 == startDate.compareTo(endDate)){
            return true;
        }
        return false;
    }

    /**
     * 获得指定格式的日期
     * @param format
     * @param date
     * @return
     */
    public static Date getDate2Date(String format, Date date){
        return getStrToDate(format, getDate2Str(format, date));
    }

    /**
     * 获得指定月的最后一天
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static void main(String[] args) {
//        Date beginA = getStrToDate("yyyy-MM-dd HH:mm", "2017-9-26 00:59");
//        System.out.println("startDay =" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getDayStart(new Date())));
//        System.out.println("endDay =" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getDayEnd(new Date())));
//        System.out.println("beginA =" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(beginA));
//        System.out.println("beginB =" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
//        System.out.println(daysBetweenTwoYear(new Date(), beginA));
//        System.out.println(hour2Minute("02:03"));
//        System.out.println(minute2Hour(123));
//        System.out.println(day2Week("2018-05-13"));
//        System.out.println(getSeconds(new Date()));
//        String date1 = "2018-09-12 11:22:33";
//        String date2 = "2018-09-12 11:22:31";
//        Date dateS = getStrToDate("yyyy-MM-dd hh:mm",date1);
//        Date dateD = getStrToDate("yyyy-MM-dd hh:mm",date2);
//        System.out.println("result = " + isSameDate(dateS,dateD));
        System.out.println(getMonthLastDay(new Date()));

    }


}
