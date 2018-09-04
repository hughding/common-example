package pers.hugh.common.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.currentTimeMillis;

/**
 * zhkang 2017-9-15 17:06:10
 */
public class DateUtil {

    public static final DateUtil instance = new DateUtil();

    public final static String HMS = "HH:mm:ss";
    public final static String YMD = "yyyyMMdd";
    public final static String YMDHMS = "yyyyMMddHHmmss";
    public final static String YMD_SPRIT = "yyyy/MM/dd";
    public final static String YMDHMS_SPRIT = "yyyy/MM/dd HH:mm:ss";
    public final static String YMD_UNDERLINED = "yyyy-MM-dd";
    public static final String YMDM_UNDERLINED = "yyyy-MM-dd HH:mm";
    public final static String YMDHMS_UNDERLINED = "yyyy-MM-dd HH:mm:ss";//24小时制
    public final static String YMDHMSS_NO_UNDERLINED_EN = "yyyyMMddHHmmssS";
    public final static String YMDHMSS_NO_DERLINED_CN = "yyyy年MM月dd日 HH点mm分ss秒";

    public static final long m_second = 1000;
    public static final long m_minute = m_second * 60;
    public static final long m_hour = m_minute * 60;
    public static final long m_day = m_hour * 24;

    public static final String TYPE_DAY = "D";
    public static final String TYPE_HOUR = "H";
    public static final String TYPE_MINUTE = "M";
    public static final String TYPE_SECOND = "S";


    /**
     * <p>DateUtils instances should NOT be constructed in standard programming.</p>
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.</p>
     */
    public DateUtil() {
    }

    /**
     * 获取系统时间戳，毫秒级
     *
     * @return
     */
    public static final long timeMillis() {
        return currentTimeMillis();
    }

    /**
     * 当前日期字符串，yyyy-MM-dd
     *
     * @return
     */
    public static final String currentDateStr() {
        return formatDate(currentTime());
    }

    /**
     * 获取当前日期
     * <br>参见{@link #timeMillis()}
     *
     * @return
     */
    public static final Date currentTime() {
        return new Date();
    }

    /**
     * 当前timestamp字符串，yyyy-MM-dd HH:mm:ss
     * <br>参见{@link #format(Date, String)}
     *
     * @return
     */
    public static final String getCurFullTimestampStr() {
        return format(getCurFullTimestamp());
    }

    /**
     * 当前timestamp
     * <br>字符串类型返回
     *
     * @return
     */
    public static final Timestamp getCurFullTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    /**
     * 当前月份的下一个月
     * <br>1月份的下一个月为 2，12月份的下一个月为1
     *
     * @return
     */
    public static final int nextMonth() {
        String next = format(new Date(), "M");
        int nextMonth = Integer.parseInt(next) + 1;
        if (nextMonth == 13) return 1;
        return nextMonth;
    }

    /**
     * parse date using default pattern yyyy-MM-dd
     *
     * @param strDate
     * @return 失败返回null
     */
    public static final Date parseDate(String strDate) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(YMD_UNDERLINED);
            date = dateFormat.parse(strDate);
            return date;
        } catch (Exception pe) {
            return null;
        }
    }

    public static int getWeekOfYear(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDateFromTimestamp(time));
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 中国传统意义的周，周一做为开始
     *
     * @param time
     * @return
     */
    public static int getCnWeekOfYear(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDateFromTimestamp(time));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 根据date字符串，获取timestamp
     *
     * @param strDate 必须为 yyyy-MM-dd hh:mm:ss[.fffffffff]格式
     * @return 失败返回null
     */
    public static final Timestamp parseTimestamp(String strDate) {
        try {
            // Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]
            Timestamp result = Timestamp.valueOf(strDate);
            return result;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param strDate
     * @param pattern
     * @return
     */
    public static final Timestamp parseTimestamp(String strDate, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(strDate);
            return new Timestamp(date.getTime());
        } catch (Exception pe) {
            return null;
        }
    }


    /**
     * @param strDate
     * @param pattern
     * @return
     */
    public static final Date parseDate(String strDate, String pattern) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(pattern);
        try {
            date = df.parse(strDate);
            return date;
        } catch (Exception pe) {
            return null;
        }
    }

    /**
     * @param date
     * @return formated date by yyyy-MM-dd
     */
    public static final <T extends Date> String formatDate(T date) {
        if (date == null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(YMD_UNDERLINED);
        return dateFormat.format(date);
    }

    /**
     * @param date
     * @return formated time by HH:mm:ss
     */
    public static final <T extends Date> String formatTime(T date) {
        if (date == null) return null;
        SimpleDateFormat timeFormat = new SimpleDateFormat(HMS);
        return timeFormat.format(date);
    }

    /**
     * @param date
     * @return formated time by yyyy-MM-dd HH:mm:ss
     */
    public static final <T extends Date> String format(T date) {
        if (date == null) return null;
        SimpleDateFormat timestampFormat = new SimpleDateFormat(YMDHMS_UNDERLINED);
        return timestampFormat.format(date);
    }

    public static final String formatTimestamp(Long mills) {
        return format(new Date(mills));
    }

    /**
     * @param date
     * @param pattern: Date format pattern
     * @return
     */
    public static final <T extends Date> Date formatDate(T date, String pattern) {
        String fdate = format(date, pattern);
        return parseDate(fdate, pattern);
    }

    /**
     * @param date
     * @param pattern: Date format pattern
     * @return
     */
    public static final <T extends Date> String format(T date, String pattern) {
        if (date == null) return null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            String result = df.format(date);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 增加时间
     *
     * @param original
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     * @return original+day+hour+minutes+seconds+millseconds
     */
    public static final <T extends Date> T addTime(T original, int days, int hours, int minutes, int seconds) {
        if (original == null) return null;
        long newTime = original.getTime() + m_day * days + m_hour * hours + m_minute * minutes + m_second * seconds;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    /**
     * 加减时间
     *
     * @param original
     * @param type     增加（减少）时间类型
     *                 <p>DateUtils.TYPE_DAY、DateUtils.TYPE_HOUR、DateUtils.TYPE_MINUTE、DateUtils.TYPE_SECOND</p>
     * @param n        增加（减少）N天/小时/分钟/秒
     * @return
     */
    public static final <T extends Date> T addTime(T original, String type, int n) {
        if (original == null) return null;
        long addTime = 0;
        if (StringUtils.equals(DateUtil.TYPE_DAY, type)) addTime = m_day * n;
        if (StringUtils.equals(DateUtil.TYPE_HOUR, type)) addTime = m_hour * n;
        if (StringUtils.equals(DateUtil.TYPE_MINUTE, type)) addTime = m_minute * n;
        if (StringUtils.equals(DateUtil.TYPE_SECOND, type)) addTime = m_second * n;
        long newTime = original.getTime() + addTime;
        T another = (T) original.clone();
        another.setTime(newTime);
        return another;
    }

    /**
     * 获取某天的一天开始时间
     *
     * @param day
     * @return for example ,1997/01/02 22:03:00,return 1997/01/02 00:00:00.0
     */
    public static final <T extends Date> T getBeginningTimeOfDay(T day) {
        if (day == null) return null;
        //new Date(0)=Thu Jan 01 08:00:00 CST 1970
        String strDate = formatDate(day);
        Long mill = parseDate(strDate).getTime();
        T another = (T) day.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * 获取某天的一天结束点,
     * <p>for example : 1997/01/02 22:03:00,return 1997/01/02 23:59:59.999</p>
     *
     * @param day
     * @return
     */
    public static final <T extends Date> T getLastTimeOfDay(T day) {
        if (day == null) return null;
        Long mill = getBeginningTimeOfDay(day).getTime() + m_day - 1;
        T another = (T) day.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * 字符串时间表示方式中去零
     * for example，09:00:00,09:07:00 ---> 9:00,9:7:00
     *
     * @param time
     * @return
     */
    public static final String formatTime(String time) {
        if (time == null) return null;
        time = StringUtils.trim(time);
        if (StringUtils.isBlank(time)) throw new IllegalArgumentException("时间格式有错误！");
        time = time.replace('：', ':');
        String[] times = time.split(":");
        String result = "";
        if (times[0].length() < 2) result += "0" + times[0] + ":";
        else result += times[0] + ":";
        if (times.length > 1) {
            if (times[1].length() < 2) result += "0" + times[1];
            else result += times[1];
        } else {
            result += "00";
        }
        Timestamp.valueOf("2001-01-01 " + result + ":00");
        return result;
    }

    public static boolean isTomorrow(Date date) {
        if (date == null) return false;
        return formatDate(addTime(new Date(), 1, 0, 0, 0)).equals(formatDate(date));
    }

    private static int[] chweek = new int[]{0, 7, 1, 2, 3, 4, 5, 6};

    /**
     * @param date
     * @return 1, 2, 3, 4, 5, 6, 7
     */
    public static Integer getWeek(Date date) {
        if (date == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return chweek[c.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * 获取当前周第N天的日期
     *
     * @param dayOfWeek 从周日开始的当天周的第几天：1,2,3,4,5,6,7
     *                  <p>for example:当前时间2016-12-28周三，则本周第一天为2016-12-25周日</p>
     * @return
     */
    public static Date getCurDateByWeek(Integer dayOfWeek) {
        if (dayOfWeek == null || dayOfWeek < 0 || dayOfWeek > 7) return DateUtil.currentTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getTime();
    }

    /**
     * 获取当前周第N天的日期
     *
     * @param dayOfWeek 从周一开始的当天周的第几天：1,2,3,4,5,6,7
     *                  <p>for example:当前时间2016-12-28周三，则本周第一天为2016-12-26周一</p>
     * @return
     */
    public static Date getCurCnDateByWeek(Integer dayOfWeek) {
        if (dayOfWeek == null || dayOfWeek <= 0 || dayOfWeek > 7) return DateUtil.currentTime();
        Date date = getCurDateByWeek(dayOfWeek);
        return addTime(date, TYPE_DAY, 1);
    }

    private static String[] cnweek = new String[]{"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static String[] cnSimpleweek = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};

    /**
     * 获取日期为周几
     *
     * @param date
     * @return "周日", "周一", "周二", "周三", "周四", "周五", "周六"
     */
    public static String getCnWeek(Date date) {
        if (date == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return cnweek[c.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * 获取日期为周几
     *
     * @param date
     * @return "日", "一", "二", "三", "四", "五", "六"
     */
    public static String getCnSimpleWeek(Date date) {
        if (date == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return cnSimpleweek[c.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * 获取当前日期为本月的第几天
     *
     * @return
     */
    public static Integer getCurrentDay() {
        return getDay(new Date());
    }

    /**
     * 获取当前日期月份
     *
     * @return
     */
    public static Integer getCurrentMonth() {
        return getMonth(new Date());
    }

    /**
     * 获取当前日期年份
     *
     * @return
     */
    public static Integer getCurrentYear() {
        return getYear(new Date());
    }

    /**
     * 获取日期年份
     *
     * @return
     */
    public static Integer getYear(Date date) {
        if (date == null) return null;
        String year = DateUtil.format(date, "yyyy");
        return Integer.parseInt(year);
    }

    /**
     * 获取指定日期为第几天
     *
     * @param date
     * @return
     */
    public static Integer getDay(Date date) {
        if (date == null) return null;
        String year = DateUtil.format(date, "d");
        return Integer.parseInt(year);
    }

    /**
     * 获取日期月份
     *
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        if (date == null) return null;
        String month = format(date, "M");
        return Integer.parseInt(month);
    }

    /**
     * 获取日期小时数
     *
     * @param date
     * @return
     */
    public static Integer getCurrentHour(Date date) {
        if (date == null) return null;
        String hour = DateUtil.format(date, "H");
        return Integer.parseInt(hour);
    }

    /**
     * 获取日期分钟数
     *
     * @param date
     * @return
     */
    public static Integer getCurrentMin(Date date) {
        if (date == null) return null;
        String hour = DateUtil.format(date, "m");
        return Integer.parseInt(hour);
    }

    /**
     * 获取当前日期的字符串格式（yyyy-MM-dd）
     *
     * @param
     * @return
     */
    public static String getCurDateStr() {
        return DateUtil.formatDate(new Date());
    }

    /**
     * 获取当前日期的字符串格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param
     * @return
     */
    public static String getCurTimeStr() {
        return DateUtil.format(new Date());
    }

    /**
     * 是否晚于当前日期
     *
     * @param date
     * @return
     */
    public static boolean isAfter(Date date) {
        if (date == null) return false;
        return date.after(new Date());
    }

    /**
     * 获取date所在月份，date日期之后（或等于）的周，星期为weektype的所有日期
     *
     * @param weektype 0,1,2,3,4,5,6  其中，0=7均表示周的第一天，同样1=8,2=9 ... ...
     *                 <p>for example:当前日期为201-01-05，weektype为0或者7则返回从下一周开始的"2017-01-08","2017-01-15","2017-01-22","2017-01-29"</p>
     * @return
     */
    public static List<Date> getWeekDateList(Date date, String weektype) {
        int curMonth = getMonth(date);
        int week = Integer.parseInt(weektype);
        int curWeek = getWeek(date);
        int sub = (7 + week - curWeek) % 7;
        Date next = addTime(date, TYPE_DAY, sub);
        List<Date> result = new ArrayList<Date>();
        while (getMonth(next) == curMonth) {
            result.add(next);
            next = addTime(next, TYPE_DAY, 7);
        }
        return result;
    }

    /**
     * 获取date之后(包括date)的第num个星期内为weektype日期（不限制月份）
     *
     * @param weektype
     * @return
     */
    public static List<Date> getWeekDateList(Date date, String weektype, int num) {
        int week = Integer.parseInt(weektype);
        int curWeek = getWeek(date);
        List<Date> result = new ArrayList<Date>();
        int sub = (7 + week - curWeek) % 7;
        Date next = addTime(date, TYPE_DAY, sub);
        for (int i = 0; i < num; i++) {
            result.add(next);
            next = addTime(next, TYPE_DAY, 7);
        }
        return result;
    }

    /**
     * 获取date所在星期的周一至周日的日期
     *
     * @param date
     * @return
     */
    public static List<Date> getCurWeekDateList(Date date) {
        int curWeek = getWeek(date);
        List<Date> dateList = new ArrayList<Date>();
        for (int i = 1; i <= 7; i++) dateList.add(DateUtil.addTime(date, TYPE_DAY, -curWeek + i));
        return dateList;
    }

    public static Date getWeekLastDay(Date date) {
        int curWeek = getWeek(date);
        return DateUtil.addTime(date, TYPE_DAY, 7 - curWeek);
    }

    public static Date getCurDate() {
        return getBeginningTimeOfDay(new Date());
    }

    /**
     * 获取日期所在月份的第一天
     *
     * @param date
     * @return
     */
    public static <T extends Date> T getMonthFirstDay(T date) {
        if (date == null) return null;
        String dateStr = format(date, "yyyy-MM") + "-01";
        Long mill = parseDate(dateStr).getTime();
        T another = (T) date.clone();
        another.setTime(mill);
        return another;
    }

    public static <T extends Date> T getNextMonthFirstDay(T date) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String datefor = format(calendar.getTime(), "yyyy-MM-dd");
        Long mill = parseDate(datefor).getTime();
        T another = (T) date.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * 获取日期所在月份的最后一天
     *
     * @param date
     * @return
     */
    public static <T extends Date> T getMonthLastDay(T date) {
        if (date == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dateStr = format(date, "yyyy-MM") + "-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
        Long mill = parseDate(dateStr).getTime();
        T another = (T) date.clone();
        another.setTime(mill);
        return another;
    }

    /**
     * 截取时分秒后的时间长
     *
     * @return
     */
    public static Long getCurTruncTimestamp() {
        long mils = System.currentTimeMillis();
        return mils - getBeginTimestamp(new Timestamp(mils)).getTime();
    }

    public static Integer getHour(Date date) {
        if (date == null) return null;
        String hour = format(date, "H");
        return Integer.parseInt(hour);
    }

    public static Integer getMinute(Date date) {
        if (date == null) return null;
        String m = format(date, "m");
        return Integer.parseInt(m);
    }

    public static String getTimeDesc(Timestamp time) {
        if (time == null) return "";
        String timeContent;
        Long ss = currentTimeMillis() - time.getTime();
        Long minute = ss / 60000;
        if (minute < 1) {
            Long second = ss / 1000;
            timeContent = second + "秒前";
        } else if (minute >= 60) {
            Long hour = minute / 60;
            if (hour >= 24) {
                if (hour > 720) timeContent = "1月前";
                else if (hour > 168 && hour <= 720) timeContent = (hour / 168) + "周前";
                else timeContent = (hour / 24) + "天前";
            } else {
                timeContent = hour + "小时前";
            }
        } else {
            timeContent = minute + "分钟前";
        }
        return timeContent;
    }

    public static String getDateDesc(Date time) {
        if (time == null) return "";
        String timeContent;
        Long ss = currentTimeMillis() - time.getTime();
        Long minute = ss / 60000;
        if (minute < 1) {
            Long second = ss / 1000;
            timeContent = second + "秒前";
        } else if (minute >= 60) {
            Long hour = minute / 60;
            if (hour >= 24) {
                if (hour > 720) timeContent = "1月前";
                else if (hour > 168 && hour <= 720) timeContent = (hour / 168) + "周前";
                else timeContent = (hour / 24) + "天前";
            } else {
                timeContent = hour + "小时前";
            }
        } else {
            timeContent = minute + "分钟前";
        }
        return timeContent;
    }

    /**
     * 截取日期, 去掉年份
     * eg. 传入"1986-07-28", 返回 07-28
     */
    public static String getMonthAndDay(Date date) {
        return formatDate(date).substring(5);
    }

    public static Date getMillDate() {
        return new Date();
    }

    public static Timestamp getMillTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    /**
     * 时间差：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> String getDiffDayStr(T day1, T day2) {
        if (day1 == null || day2 == null) return "---";
        long diff = day1.getTime() - day2.getTime();
        long sign = diff / Math.abs(diff);
        if (sign < 0) return "已经过期";
        diff = Math.abs(diff) / 1000;
        long day = diff / 3600 / 24;
        long hour = (diff - (day * 3600 * 24)) / 3600;
        long minu = diff % 3600 / 60;
        return (day == 0 ? "" : day + "天") + (hour == 0 ? "" : hour + "小时") + (minu == 0 ? "" : minu + "分");
    }

    /**
     * 时间差：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> String getDiffStr(T day1, T day2) {
        if (day1 == null || day2 == null) return "---";
        long diff = day1.getTime() - day2.getTime();
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        long hour = diff / 3600;
        long minu = diff % 3600 / 60;
        long second = diff % 60;
        return (sign < 0 ? "-" : "+") + (hour == 0 ? "" : hour + "小时") + (minu == 0 ? "" : minu + "分") + (second == 0 ? "" : second + "秒");
    }

    /**
     * 时间差（秒）：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> long getDiffSecond(T day1, T day2) {
        if (day1 == null || day2 == null) return 0;
        long diff = day1.getTime() - day2.getTime();
        if (diff == 0) return 0;
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return sign * diff;
    }

    /**
     * 时间差（分钟）：day1-day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> double getDiffMinu(T day1, T day2) {
        if (day1 == null || day2 == null) return 0;
        long diff = day1.getTime() - day2.getTime();
        if (diff == 0) return 0;
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return Math.round(diff * 1.0d * 10 / 6.0) / 100.0 * sign;//两位小数
    }

    /**
     * 时间差（分）：time1 - time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static final double getMillDiffMinu(long time1, long time2) {
        long diff = time1 - time2;
        if (diff == 0) return 0;
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return Math.round(diff * 1.0d * 10 / 6.0) / 100.0 * sign;//两位小数
    }

    /**
     * 时间差（小时）：day1 - day2
     *
     * @param day1
     * @param day2
     * @return
     */
    public static final <T extends Date> double getDiffHour(T day1, T day2) {
        if (day1 == null || day2 == null) return 0;
        long diff = day1.getTime() - day2.getTime();
        long sign = diff / Math.abs(diff);
        diff = Math.abs(diff) / 1000;
        return Math.round(diff * 1.0d / 3.6) / 1000.0 * sign;//三位小数
    }

    /**
     * @param day1
     * @param day2
     * @return 日期相差整数round(abst（day1-day2))
     */
    public static final <T extends Date> int getDiffDay(T day1, T day2) {
        if (day1 == null || day2 == null) return 0;
        long diff = day1.getTime() - day2.getTime();
        diff = Math.abs(diff) / 1000;
        return Math.round(diff / (3600 * 24));
    }

    public static boolean isAfterOneHour(Date date, String time) {
        String datetime = formatDate(date) + " " + time + ":00";
        return addTime(parseTimestamp(datetime), TYPE_HOUR, -1).after(getMillTimestamp());
    }

    public static boolean isValidDate(String fyrq) {
        return DateUtil.parseDate(fyrq) != null;
    }

    public static <T extends Date> long getCurDateMills(T date) {
        if (date == null) return 0;
        return date.getTime();
    }

    /**
     * eg.  1997/01/02 22:03:00,return 1997/01/02 00:00:00.0
     **/
    public static Timestamp getBeginTimestamp(Date date) {
        return new Timestamp(getBeginningTimeOfDay(date).getTime());
    }

    public static Timestamp getEndTimestamp(Date date) {
        return new Timestamp(getLastTimeOfDay(date).getTime());
    }

    /**
     * @param timestamp
     * @return
     */
    public static Date getDateFromTimestamp(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new Date(timestamp.getTime());
    }

    public static int after(Date date1, Date date2) {
        date1 = getBeginningTimeOfDay(date1);
        date2 = getBeginningTimeOfDay(date2);
        return date1.compareTo(date2);
    }

    public static Timestamp mill2Timestamp(Long mill) {
        if (mill == null) return null;
        return new Timestamp(mill);
    }

    public static int subCurTimeSend() {
        Timestamp curtime = DateUtil.getCurFullTimestamp();
        Timestamp endtime = DateUtil.getLastTimeOfDay(curtime);
        Long scopeSecond = DateUtil.getDiffSecond(endtime, curtime);
        return scopeSecond.intValue();
    }

    /**
     * @param date
     * @param pattern: Date format pattern
     * @return
     */
    public static final <T extends Date> String formatEn(T date, String pattern) {
        if (date == null) return null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
            String result = df.format(date);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static long getIntervalMinutes(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        return (end - start) / (60 * 1000);
    }

    public static long getDurationMinutes(String start, String end) {
        return (parseDate(end, YMDM_UNDERLINED).getTime() - parseDate(start, YMDM_UNDERLINED).getTime()) / (1000 * 60);
    }

    public static String getIso8601DurationByMinute(long minute) {
        if (minute <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("P");
        // 暂不考虑闰年情况
        if (minute > 12 * 30 * 24 * 60) {
            int year = (int) (minute / (12 * 30 * 24 * 60));
            sb.append(year).append("Y");
            minute = minute - year * 12 * 30 * 24 * 60;
        }
        if (minute > 30 * 24 * 60) {
            int mouth = (int) (minute / (30 * 24 * 60));
            sb.append(mouth).append("M");
            minute = minute - mouth * 30 * 24 * 60;
        }
        if (minute > 24 * 60) {
            int day = (int) (minute / (24 * 60));
            sb.append(day).append("D");
            minute = minute - day * 24 * 60;
        }
        sb.append("T");
        if (minute > 60) {
            int hour = (int) (minute / 60);
            sb.append(hour).append("H");
            minute = minute - hour * 60;
        }
        if (minute != 0) {
            sb.append(minute).append("M");
        }
        return sb.toString();
    }

}