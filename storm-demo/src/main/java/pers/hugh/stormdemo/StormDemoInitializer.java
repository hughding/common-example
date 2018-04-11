package pers.hugh.stormdemo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pers.hugh.stormdemo.bolt.FormatBolt;
import pers.hugh.stormdemo.bolt.StatisticBolt;
import pers.hugh.stormdemo.spout.WebLogSpout;

import java.util.concurrent.TimeUnit;

/**
 * ·Shuffle grouping（随机分组）：这种方式会随机分发tuple给bolt的各个task，每个bolt实例接收到的相
 * 同数量的tuple。
 * ·Fields grouping（按字段分组）：根据指定字段的值进行分组。比如说，一个数据流根据“word”字段
 * 进行分组，所有具有相同“word”字段值的tuple会路由到同一个bolt的task中。
 * ·All grouping（全复制分组）：将所有的tuple复制后分发给所有bolt task。每个订阅数据流的task都会接
 * 收到tuple的拷贝。
 * ·Globle grouping（全局分组）：这种分组方式将所有的tuples路由到唯一一个task上。Storm按照最小的
 * task ID来选取接收数据的task。注意，当使用全局分组方式时，设置bolt的task并发度是没有意义的，因为
 * 所有tuple都转发到同一个task上了。使用全局分组的时候需要注意，因为所有的tuple都转发到一个JVM实
 * 例上，可能会引起Storm集群中某个JVM或者服务器出现性能瓶颈或崩溃。
 * ·None grouping（不分组）：在功能上和随机分组相同，是为将来预留的。
 * ·Direct grouping（指向型分组）：数据源会调用emitDirect（）方法来判断一个tuple应该由哪个Storm组
 * 件来接收。只能在声明了是指向型的数据流上使用。
 * ·Local or shuffle grouping（本地或随机分组）：和随机分组类似，但是，会将tuple分发给同一个worker
 * 内的bolt task（如果worker内有接收数据的bolt task）。其他情况下，采用随机分组的方式。取决于topology
 * 的并发度，本地或随机分组可以减少网络传输，从而提高topology性能。
 *
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/11</pre>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"pers.hugh.stormdemo","pers.hugh.common.util"})
public class StormDemoInitializer {


    private static final String TOPOLOGY_NAME = "weblog-topology";
    private static final String WEBLOG_SPOUT_ID = "weblog-spout";
    private static final String FORMAT_BOLT_ID = "format-bolt";
    private static final String STATISTIC_BOLT_ID = "statistic-bolt";

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(StormDemoInitializer.class);

        TopologyBuilder builder = new TopologyBuilder();
        WebLogSpout webLogSpout = new WebLogSpout();
        FormatBolt formatBolt = new FormatBolt();
        StatisticBolt statisticBolt = new StatisticBolt();
        builder.setSpout(WEBLOG_SPOUT_ID, webLogSpout, 1);
        //StatisticBolt单词分割器设置4个Task，2个Executeor(线程), shuffleGrouping方法告诉Storm要将WebLogSpout发射的tuple随机均匀的分发给StatisticBolt的实例
        builder.setBolt(FORMAT_BOLT_ID, formatBolt, 2).setNumTasks(4).shuffleGrouping(WEBLOG_SPOUT_ID);
        builder.setBolt(STATISTIC_BOLT_ID, statisticBolt, 1).fieldsGrouping(FORMAT_BOLT_ID, new Fields("formatWebLog"));

        Config conf = new Config();
        conf.setDebug(false);
        if (args != null && args.length > 0) {
            // 集群模式
            conf.setNumWorkers(2);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            // 本地模式
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(TOPOLOGY_NAME, conf, builder.createTopology());
            TimeUnit.MILLISECONDS.sleep(6000000);
            cluster.shutdown();
        }
    }
}
