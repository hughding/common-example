package pers.hugh.common.practice.algorithm;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @date 2018/8/26
 */
public class ChineseTradeDayUtil {

    /**
     * 一年天数最多为366天，闰年。除闰年外为365天
     */
    private static final int MAX_DAY_NUM_ONE_YEAR = 366;

    /**
     * 日期格式
     */
    private static final String FORMAT = "yyyyMMddHHmm";

    /**
     * 时区，北京时间
     */
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+08:00");

    /**
     * 交易开始时间09:30，include
     */
    private static final int TIME_LOW = 930;

    /**
     * 交易结束时间15:00，exclude
     */
    private static final int TIME_HIGH = 1500;

    /**
     * 用来记录一年中的所有的tradeDay, 每月最多31天，最多12个月。
     * index = month * 31 + day - 1, 有值，则值为交易日，无值，则不是交易日
     */
    private String[] tradeDayRecord = new String[31 * 12 + 1];

    /**
     * 获取tradeDay在tradeDayRecord中的index
     *
     * @param tradeDay 格式yyyyMMdd
     * @return
     */
    private int getTradeDayRecordIndex(String tradeDay) {
        int mon = Integer.valueOf(tradeDay.substring(4, 6));
        int day = Integer.valueOf(tradeDay.substring(6));
        return (mon-1) * 31 + day - 1;
    }

    /**
     * 工具初始化，初始化的目的是让工具具备更加合适的数据结构，方便计算提高效率
     *
     * @param tradeDayList 包含一年内所有的交易日，格式如 20160701 20160702
     */
    public void init(List<String> tradeDayList) {
        //如果tradeDayList为空，或者超过一年的日期量
        if (tradeDayList == null || tradeDayList.size() > MAX_DAY_NUM_ONE_YEAR) {
            throw new IllegalArgumentException("tradeDayList exception " + tradeDayList);
        }
        for (String tradeDay : tradeDayList) {
            if (tradeDay == null || tradeDay.length() != 8 || !isNumeric(tradeDay)) {
                throw new IllegalArgumentException("tradeDay exception " + tradeDay);
            }
            tradeDayRecord[getTradeDayRecordIndex(tradeDay)] = tradeDay;
        }
    }

    /**
     * 给定任意时间，返回给定时间的T+n交易日,若无交易日，返回null
     *
     * @param time       给定要计算的时间
     * @param offsetDays 交易日便宜量，offsetDays可以为负数，表示T-n的计算
     * @return
     */
    public String getTradeDay(Date time, int offsetDays) {
        if (time == null) {
            throw new IllegalArgumentException("time is null");
        }
        //yyyyMMddHHmm
        String timeString = date2String(time);
        //yyyyMMdd
        String dayString = timeString.substring(0, 8);
        //HHmm
        int hourAndmin = Integer.valueOf(timeString.substring(8));
        int tradeDayRecordIndex = getTradeDayRecordIndex(dayString);
        int startIndex = tradeDayRecordIndex;

        //获取当前交易日T+0的tradeDayRecordIndex
        if(tradeDayRecord[tradeDayRecordIndex] == null || hourAndmin >= TIME_HIGH ){
            if(tradeDayRecord[tradeDayRecordIndex] != null){
                if(++tradeDayRecordIndex >= tradeDayRecord.length){
                    if(offsetDays > 0){
                        return null;
                    }else{
                        tradeDayRecordIndex = startIndex + 1;
                    }
                }
            }
            while (tradeDayRecord[tradeDayRecordIndex] == null) {
                if(++tradeDayRecordIndex >= tradeDayRecord.length){
                    if(offsetDays > 0){
                        return null;
                    }else{
                        tradeDayRecordIndex = startIndex + 1;
                        break;
                    }
                }
            }
        }

        String record = tradeDayRecordIndex < tradeDayRecord.length ? tradeDayRecord[tradeDayRecordIndex] : null;
        if (offsetDays == 0 && record != null) {
            return record;
        } else if (offsetDays > 0) {
            tradeDayRecordIndex++;
        } else {
            tradeDayRecordIndex--;
        }
        int i = 0;
        while (i != offsetDays) {
            if (tradeDayRecordIndex >= tradeDayRecord.length || tradeDayRecordIndex < 0) {
                return null;
            }
            record = tradeDayRecord[tradeDayRecordIndex];
            if (offsetDays >= 0) {
                tradeDayRecordIndex++;
                if (record != null) {
                    i++;
                }
            } else {
                tradeDayRecordIndex--;
                if (record != null) {
                    i--;
                }
            }
        }
        return record;
    }

    /**
     * 日期转化为String，格式为yyyyMMddHHmm
     *
     * @param date
     * @return yyyyMMddHHmm
     */
    private String date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        formatter.setTimeZone(TIME_ZONE);
        return formatter.format(date);
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        ChineseTradeDayUtil chineseTradeDayUtil = new ChineseTradeDayUtil();
        List<String> tradeDayList = Arrays.asList("20180822", "20180823", "20180824", "20180827", "20180828", "20180829");
        chineseTradeDayUtil.init(tradeDayList);
        Date date = null;

        //中间值，不在交易日期内
        date = parseDate("2018-08-26 06:00");
        System.out.println("======================test======================");
        System.out.println("20180827".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180828".equals(chineseTradeDayUtil.getTradeDay(date, 1)));
        System.out.println("20180824".equals(chineseTradeDayUtil.getTradeDay(date, -1)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 2)));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -3)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 3));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -4));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //中间值，在交易日期内，当天15点之前
        date = parseDate("2018-08-27 06:00");
        System.out.println("======================test======================");
        System.out.println("20180827".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180828".equals(chineseTradeDayUtil.getTradeDay(date, 1)));
        System.out.println("20180824".equals(chineseTradeDayUtil.getTradeDay(date, -1)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 2)));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -3)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 3));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -4));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //中间值，在交易日期内，当天15点之后
        date = parseDate("2018-08-27 15:00");
        System.out.println("======================test======================");
        System.out.println("20180828".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 1)));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -4)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 2));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -5));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最前，在交易日期内，当天15点之前
        date = parseDate("2018-08-22 06:00");
        System.out.println("======================test======================");
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 5)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 6));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最前，在交易日期内，当天15点之后
        date = parseDate("2018-08-22 15:00");
        System.out.println("======================test======================");
        System.out.println("20180823".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 4)));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -1)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 5));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -2));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最前，不在交易日期内，当天15点之前
        date = parseDate("2018-08-21 06:00");
        System.out.println("======================test======================");
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 5)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 6));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最前，不在交易日期内，当天15点之后
        date = parseDate("2018-08-21 15:00");
        System.out.println("======================test======================");
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 5)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 6));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最后，在交易日期内，当天15点之前
        date = parseDate("2018-08-29 06:00");
        System.out.println("======================test======================");
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -5)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -6));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最后，在交易日期内，当天15点之后
        date = parseDate("2018-08-29 15:00");
        System.out.println("======================test======================");
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 0));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -6)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -7));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最后，不在交易日期内，当天15点之前
        date = parseDate("2018-08-30 06:00");
        System.out.println("======================test======================");
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 0));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -6)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -7));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //最后，不在交易日期内，当天15点之后
        date = parseDate("2018-08-30 15:00");
        System.out.println("======================test======================");
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 0));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -6)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 1));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -7));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));

        //后边界
        date = parseDate("2018-12-31 15:00");
        System.out.println("======================test======================");
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 0));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 1));
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, -6)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -7));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));
        //前边界
        date = parseDate("2018-01-01 06:00");
        System.out.println("======================test======================");
        System.out.println("20180822".equals(chineseTradeDayUtil.getTradeDay(date, 0)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -1));
        System.out.println("20180829".equals(chineseTradeDayUtil.getTradeDay(date, 5)));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 6));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -365));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -366));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -371));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, 372));
        System.out.println(null == chineseTradeDayUtil.getTradeDay(date, -372));
    }

    /**
     * help test
     *
     * @param strDate yyyy-MM-dd HH:mm
     * @return
     */
    private static Date parseDate(String strDate) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = dateFormat.parse(strDate);
            return date;
        } catch (Exception pe) {
            return null;
        }
    }
}
