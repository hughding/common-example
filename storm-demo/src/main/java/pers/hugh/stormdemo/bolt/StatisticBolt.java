package pers.hugh.stormdemo.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import pers.hugh.stormdemo.entity.FormatWebLog;
import pers.hugh.stormdemo.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对日志进行统计, 时间窗口设置为5秒
 *
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/7</pre>
 */
public class StatisticBolt extends BaseBasicBolt {

    private static final int TIME_WINDOW_SECONDS = 5;

    private static List<Tuple> cache = new ArrayList<>(100);

    private static long lastTime = TimeUtil.getCurrentTimeStamp() + TIME_WINDOW_SECONDS;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //这里是末端bolt，不需要发射数据流，这里无需定义
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        long currentTime = TimeUtil.getCurrentTimeStamp();
        if (currentTime <= lastTime) {
            cache.add(tuple);
        } else {
            //统计数量
            Map<String, Integer> cityPairMap = new HashMap<>(8);
            for (Tuple cacheTuple : cache) {
                FormatWebLog formatWebLog = (FormatWebLog) cacheTuple.getValueByField("formatWebLog");
                String cityPairKey = formatWebLog.getDepartureCity() + "-" + formatWebLog.getArrivalCity();
                Integer count = cityPairMap.get(cityPairKey);
                count = (count == null ? 1 : count + 1);
                cityPairMap.put(cityPairKey, count);
            }
            for (Map.Entry<String, Integer> entry : cityPairMap.entrySet()) {
                String[] attribute = entry.getKey().split("-");
                System.out.println(String.join(" ", "统计数据:", String.valueOf(lastTime - TIME_WINDOW_SECONDS),
                        attribute[0], attribute[1], "count:", String.valueOf(entry.getValue())));
            }

            //设置下一个时间窗口
            lastTime += TIME_WINDOW_SECONDS;
            cache.clear();
            cache.add(tuple);
        }
    }
}
