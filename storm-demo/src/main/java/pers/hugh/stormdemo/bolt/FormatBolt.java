package pers.hugh.stormdemo.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import pers.hugh.common.util.JsonUtil;
import pers.hugh.stormdemo.entity.FormatWebLog;
import pers.hugh.stormdemo.entity.WebLog;

import java.util.Map;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/8</pre>
 */
public class FormatBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        WebLog webLog = (WebLog) input.getValueByField("webLog");

        Map map = JsonUtil.readJsonToMap(webLog.getContent());
        FormatWebLog formatWebLog = new FormatWebLog();
        formatWebLog.setTimeStamp(Long.valueOf(webLog.getTimestamp()));
        formatWebLog.setDepartureCity((String) map.get("departureCity"));
        formatWebLog.setArrivalCity((String) map.get("arrivalCity"));
        //向下一个bolt发射数据
        collector.emit(new Values(formatWebLog));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("formatWebLog"));
    }
}
