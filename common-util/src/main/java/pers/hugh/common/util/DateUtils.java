package pers.hugh.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String BACK_SLANT_yyyy_MM_dd = "yyyy/MM/dd";
    public static final String BACK_SLANT_yyyy_MM_ddHHmmss = "yyyy/MM/ddHH:mm:ss";
    public static final String YYYYMMDDHHmm = "yyyyMMddHHmm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyMMddHHmmssSSS = "yyMMddHHmmssSSS";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyMMddHHmmss = "yyMMddHHmmss";
    public static final String YYYY_MM_DDHHmm = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DDHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DDTHHmmss = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YYYY_MM_DDTHHmmssZ = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
    public static final String EEE_MMM_dd_HHmmss_zzz_yyyy = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final String HHmm = "HH:mm";
    public static final String MM_DD_CN = "MM月dd日";

    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+08:00");
    private final static TimeZone TIME_ZONE_GMT = TimeZone.getTimeZone("GMT");

    static {
        // 中国在1986-1991年实行过夏令时, 如果不设置default, 对那段时间的夏令时会parse成GMT+09:00, 导致计算年份差时产生bug
        TimeZone.setDefault(TIME_ZONE);
    }

    public static boolean isMoreThanTime(Date targetTime, Date now) {
        if (targetTime.getTime() <= now.getTime()) {
            return true;
        }
        return false;
    }

    public static boolean isLessThanTime(Date targetTime, Date now) {
        if (now.getTime() <= targetTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间戳
     *
     * @returnn
     */
    public static Integer now() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 将毫秒转化为了Integer时间戳
     *
     * @param millis
     * @return
     */
    public static Integer toTs(long millis) {
        return (int) (millis / 1000);
    }

    /**
     * 将Integer时间戳转化为millis
     *
     * @param ts
     * @return
     */
    public static Long toMills(Integer ts) {
        return ts.longValue() * 1000L;
    }

    /**
     * 获取时间的周,英文
     *
     * @param date
     * @return
     */
    public static String getEnWeekOfDate(Date date) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    /**
     * 获取当前日期是周几<br>
     * 
     * @param dt
     * @return 当前日期是周几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static int dayForWeek(String dateStr) {
        Date date = getDate(dateStr, YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 将一个字符串的日期描述转换为java.util.Date对象
     *
     * @param strDate 字符串的日期描述
     * @param format 字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
     * @return 字符串转换的日期对象java.util.Date
     */
    public static Date getDate(String strDate, String format) {
        return getDate(strDate, format, TIME_ZONE);
    }

    public static Date getDateGMT(String strDate, String format) {
        return getDate(strDate, format, TIME_ZONE_GMT);
    }

    public static Date getDate(String strDate, String format, TimeZone timeZone) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        setTimeZone(timeZone, formatter);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            logger.error("getDate is error.strDate={}, format={}", strDate, format, e);
        }
        return date;
    }

    private static void setTimeZone(TimeZone timeZone, SimpleDateFormat formatter) {
        if (timeZone == null) {
            formatter.setTimeZone(TIME_ZONE);
        } else {
            formatter.setTimeZone(timeZone);
        }
    }

    /**
     * 将一个字符串的日期描述转换为java.util.Date对象
     *
     * @param date 日期
     * @param format 字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
     * @return 字符串转换的String
     */
    public static String date2String(Date date, String format) {
        return date2String(date, format, TIME_ZONE);
    }

    public static String date2String(Date date, String format, TimeZone timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        setTimeZone(timeZone, formatter);
        return formatter.format(date);
    }

    public static String date2StringGMT(Date date, String format) {
        return date2String(date, format, TIME_ZONE_GMT);
    }


    /**
     * 得到指定时间day之后的日期
     * 
     * @param date
     * @param day
     * @return
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 将一个字符串的日期 天，小时，分 获取秒数
     *
     * @param timeCostStr 字符串的日期描述
     * @return 将含有天 ，小时 /时，分的数据解析为秒数
     */
    public static Long getLongTime(String timeCostStr) {
        Long h = 0L;
        if (StringUtils.isBlank(timeCostStr)) {
            return h;
        }
        if (timeCostStr.contains("小时")) {
            timeCostStr = timeCostStr.replace("小时", "时");
        }
        if (!StringUtils.isBlank(timeCostStr) && timeCostStr.contains("天")) {
            h += Long.parseLong(timeCostStr.substring(0, timeCostStr.indexOf("天"))) * 24 * 60 * 60;
            timeCostStr = timeCostStr.substring(timeCostStr.indexOf("天") + 1, timeCostStr.length());
        }
        if (!StringUtils.isBlank(timeCostStr) && timeCostStr.contains("时")) {
            h += Long.parseLong(timeCostStr.substring(0, timeCostStr.indexOf("时"))) * 60 * 60;
            timeCostStr = timeCostStr.substring(timeCostStr.indexOf("时") + 1, timeCostStr.length());
        }
        if (!StringUtils.isBlank(timeCostStr) && timeCostStr.contains("分")) {
            h += Long.parseLong(timeCostStr.substring(0, timeCostStr.indexOf("分"))) * 60;
        }
        return h;
    }

    /**
     * 获取日期所在月分
     *
     */
    public static int getMonthFromCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取月分
        int month = calendar.get(Calendar.MONTH);

        return month + 1;
    }

    /**
     * 获取日期所在年分
     *
     */
    public static int getYearFromCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取年分
        return calendar.get(Calendar.YEAR);

    }

    /**
     * ＠searchContext month 选择月份 获取该月的第一天
     *
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.MONTH) > month) {
            calendar.add(Calendar.YEAR, 1);
        }
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return date2String(calendar.getTime(), YYYY_MM_DD);

    }

    /**
     * ＠searchContext month 选择月份 获取该月的最后一天
     *
     */
    public static String getLastDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        month = month + 1;

        if (calendar.get(Calendar.MONTH) > month) {
            calendar.add(Calendar.YEAR, 1);
        }
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return date2String(calendar.getTime(), YYYY_MM_DD);

    }

    /**
     * 月份加相应数量的月份
     *
     */
    public static Date monthAddNum(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    /**
     * 秒加相应数量的秒数
     *
     */
    public static Date secondAddNum(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, num);
        return calendar.getTime();
    }

    /**
     * 分钟加相应数量的分钟数
     *
     */
    public static Date minuteAddNum(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        return calendar.getTime();
    }

    public static Date string2Date(String date, String format) {
        return string2Date(date, format, TIME_ZONE);
    }

    public static Date string2DateWithT(String date) {
        return string2Date(date, YYYY_MM_DDTHHmmss, TIME_ZONE);
    }

    public static Date string2DateGMT(String date, String format) {
        return string2Date(date, format, TIME_ZONE_GMT);
    }

    public static Date string2DateGMTWithT(String date) {
        return string2Date(date, YYYY_MM_DDTHHmmss, TIME_ZONE_GMT);
    }

    public static Date string2Date(String date, String format, TimeZone timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        setTimeZone(timeZone, formatter);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            logger.error("DateUtils string2Date is error. date={}, format={}", date, format, e);
            return null;
        }
    }

    public static Date string2DateByLocale(String date, String format, Locale locale) {
        SimpleDateFormat formatter = new SimpleDateFormat(format,locale);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            logger.error("DateUtils string2Date is error. date={}, format={}", date, format, e);
            return null;
        }
    }

    public static boolean isToday(Date date) {
        Calendar someDay = Calendar.getInstance();
        someDay.setTime(date);
        int aa = someDay.get(Calendar.DAY_OF_YEAR);
        Calendar today = Calendar.getInstance();
        int bb = today.get(Calendar.DAY_OF_YEAR);
        return aa == bb;
    }

    /**
     * 获取当前日期
     * 
     * @return 当前日期
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 是否在今天到某天中间
     * 
     * @param date
     * @param otherDate
     * @return
     */
    public static boolean inTodayAndOtherDay(Date date, Date otherDate) {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(otherDate);
        int otherDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date);
        int now = calendar.get(Calendar.DAY_OF_YEAR);

        return today <= now && otherDay >= now;

    }

    public static Date addHourByNum(Date date, int hour) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(GregorianCalendar.HOUR, hour);
            return calendar.getTime();
        } catch (Exception e) {
            logger.error("addHourByNum is error, date={}, hour={}", date, hour, e);
            return date;
        }
    }

    public static Date addDayByNum(Date date, int day) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (Exception e) {
            logger.error("addDayByNum is error, date={}, day={}", date, day, e);
            return date;
        }
    }

    public static Date addMonthByNum(Date date, int month) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(GregorianCalendar.MONTH, month);
            return calendar.getTime();
        } catch (Exception e) {
            logger.error("addDayByNum is error, date={}, month={}", date, month, e);
            return date;
        }
    }

    public static String addDayByNum(String date, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
        try {
            Date dateTemp = formatter.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateTemp);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            logger.error("addDayByNum ParseException, date={}, day={}", date, day, e);
            return date;
        }
    }

    /**
     * 获取当天的时间，时分秒和毫秒均为0
     * 
     * @return
     */
    public static Date getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 计算 date1 - date2 年份差, 需在同一时区下比较 注意一个tr
     */
    public static int getIntervalYear(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int y1 = c1.get(Calendar.YEAR);
        int y2 = c2.get(Calendar.YEAR);
        int interval = y1 - y2;

        c2.set(Calendar.YEAR, y1);
        if (c1.compareTo(c2) < 0) {
            interval--;
        }
        return interval;
    }

    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    public static int getIntervalDay(Date date1, Date date2) {
        long interval = Math.abs(date1.getTime() - date2.getTime());
        int days = (int) (interval / ONE_DAY_MS);
        return days;
    }

    /**
     * @param msel 毫秒时间
     * @param format 日期格式
     * @return 毫秒时间 所对应的日期
     */
    public static String formatDate(long msel, String format) {
        Date date = new Date(msel);
        return date2String(date, format);
    }

    public static String getHHmmTimeCn(Integer minutes) {
        if (minutes == null) {
            return StringUtils.EMPTY;
        }
        String result = StringUtils.EMPTY;
        int hours = minutes / 60;
        if (hours > 0) {
            result = result + hours + "时";
        }
        int minute = minutes % 60;
        if (minute > 0) {
            result = result + minute + "分";
        }
        return result;
    }

    /**
     * 获取两个日期相差的分钟数
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getIntervalMinutes(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        return (end - start) / (60 * 1000);
    }


    /**
     * https://en.wikipedia.org/wiki/ISO_8601#Durations
     *
     * @param duration iso 8601标准
     * @return 中文时间
     */
    public static Long getTripDurationMinutes(String duration) {
        if (StringUtils.isBlank(duration) || !duration.startsWith("P")) {
            return null;
        }

        String dealStr = duration.replace("P", "");
        String[] arrStr = dealStr.split("T");

        String ymdStr = null;
        String timeStr = null;

        if (arrStr.length == 2) {
             ymdStr = arrStr[0];
             timeStr = arrStr[1];
        }else{
             ymdStr = "";
             timeStr = arrStr[0];
        }


        String formatter = "";
        if (ymdStr.contains("Y")) {
            ymdStr = ymdStr.replace("Y", "年");
            formatter = formatter + "yyyy年";
        }
        if (ymdStr.contains("M")) {
            ymdStr = ymdStr.replace("M", "月");
            formatter = formatter + "MM月";
        }
        if (ymdStr.contains("D")) {
            ymdStr = ymdStr.replace("D", "日");
            formatter = formatter + "dd日";
        }
        if (timeStr.contains("H")) {
            timeStr = timeStr.replace("H", "时");
            formatter = formatter + "HH时";
        }
        if (timeStr.contains("M")) {
            timeStr = timeStr.replace("M", "分");
            formatter = formatter + "mm分";
        }
        if (timeStr.contains("S")) {
            timeStr = timeStr.replace("S", "秒");
            formatter = formatter + "ss秒";
        }
        Date dateTime = null;
        if (StringUtils.isNotBlank(ymdStr) && StringUtils.isNotBlank(timeStr)) {
            dateTime = DateUtils.string2Date(ymdStr + timeStr, formatter);
        } else if (StringUtils.isBlank(ymdStr) && StringUtils.isNotBlank(timeStr)) {
            dateTime = DateUtils.string2Date(timeStr, formatter);
            return getMinutes(dateTime);
        } else if (StringUtils.isNotBlank(ymdStr)) {
            dateTime = DateUtils.string2Date(ymdStr, formatter);
        }
        if (dateTime == null) {
            return null;
        }
        return getZeroYearMinutes(dateTime);
    }

    private static Long getZeroYearMinutes(Date dateTime) {
        return getIntervalMinutes(string2Date("0000-00-00", YYYY_MM_DD), dateTime);
    }

    public static long getMinutes(Date dateTime) {
        return getIntervalMinutes(string2Date("1970-01-01", YYYY_MM_DD), dateTime);
    }

    // http://www.028888.net/archives/2016_08_1480.html
    public static Date getDateWithZ(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }
        if (timeStr.contains("Z")) {
            return getDateIgnoreZ(timeStr);
        } else if (timeStr.length() == 19){
            if (timeStr.contains("T")) {
                return string2Date(timeStr, YYYY_MM_DDTHHmmss);
            } else {
                return string2Date(timeStr, YYYY_MM_DDHHmmss);
            }
        } else if (timeStr.length() >= 19) {
            return getDateIgnoreZ(timeStr);
        }
        return null;
    }

    private static Date getDateIgnoreZ(String timeStr) {
        if (StringUtils.isBlank(timeStr) || timeStr.length() < 19) {
            return null;
        }
        return string2Date(timeStr.replace("T", " ").substring(0, 19), YYYY_MM_DDHHmmss);
    }

    public static String getIso8601DurationByMinute(long minute) {
        if (minute <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("P");
        // 暂不考虑闰年情况
        if (minute > 12*30*24*60) {
            int year = (int)(minute/(12*30*24*60));
            sb.append(year).append("Y");
            minute = minute - year * 12*30*24*60;
        }
        if (minute > 30 * 24 * 60) {
            int mouth = (int)(minute/(30 * 24 * 60));
            sb.append(mouth).append("M");
            minute = minute - mouth * 30 * 24 * 60;
        }
        if (minute > 24 * 60) {
            int day = (int)(minute/(24 * 60));
            sb.append(day).append("D");
            minute = minute - day * 24*60;
        }
        sb.append("T");
        if (minute > 60) {
            int hour = (int)(minute/60);
            sb.append(hour).append("H");
            minute = minute - hour *60;
        }
        if (minute != 0) {
            sb.append(minute).append("M");
        }
        return sb.toString();
    }


    public static long getDurationMinutes(String start, String end){
        return (string2Date(end,YYYY_MM_DDHHmm).getTime() - string2Date(start,YYYY_MM_DDHHmm).getTime()) / (1000 * 60);
    }

    public static String dealTAndZ(String datetime) {
        if (StringUtils.isBlank(datetime) || datetime.contains("Z") || datetime.contains("T")) {
            return StringUtils.EMPTY;
        }
        return datetime.replace(" ", "T") + ":00Z";
    }

    /**
     * 获取整形时间
     *
     * @param timeStr HH:mm
     * @return HHmm的数值
     */
    public static int getIntTime(String timeStr) {
        return Integer.parseInt(timeStr.replace(":", ""));
    }

    public static void main(String[] args) {
        System.out.println(getIso8601DurationByMinute(24 * 60 * 10 + 3));
    }
}
