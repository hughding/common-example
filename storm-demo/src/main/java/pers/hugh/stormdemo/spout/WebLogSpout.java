package pers.hugh.stormdemo.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import pers.hugh.common.util.SpringUtil;
import pers.hugh.stormdemo.consumer.WebLogConsumer;
import pers.hugh.stormdemo.entity.WebLog;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/7</pre>
 */
public class WebLogSpout extends BaseRichSpout{

    private BlockingQueue<WebLog> queue;

    private SpoutOutputCollector collector;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //定义输出字段描述
        outputFieldsDeclarer.declare(new Fields("webLog"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.queue = SpringUtil.getBean(WebLogConsumer.class).getQueue();
        //让消费者模拟产生数据
        SpringUtil.getBean(WebLogConsumer.class).onMessage();
    }

    @Override
    public void nextTuple() {
        WebLog data = this.queue.poll();
        if(data == null){
            try {
                TimeUnit.MILLISECONDS.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            this.collector.emit(new Values(data));
        }
    }

}
