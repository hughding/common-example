package pers.hugh.stormdemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/11</pre>
 */
public class TimeUtil {
    /**
     * 获取当前时间的时间戳
     *
     * @return 格式：yyyyMMddHHmmss
     */
    public static long getCurrentTimeStamp() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = dataFormat.format(new Date());
        return Long.valueOf(timeStamp);
    }
}
