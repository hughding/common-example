package pers.hugh.stormdemo.consumer;

import org.springframework.stereotype.Component;
import pers.hugh.stormdemo.entity.WebLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/11</pre>
 */
@Component
public class WebLogConsumer {
    private volatile BlockingQueue<WebLog> queue = new LinkedBlockingQueue<>();

    /**
     * 模拟消费者，读取数据
     */
    public void onMessage() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                String contentJson = "{\"departureCity\":\"London\",\"arrivalCity\":\"Paris\"}";
                Map<String, String> map = new HashMap<>();
                map.put("timestamp", "201804112039");
                map.put("content", contentJson);
                Message msg = new Message(map);

                String timestamp = msg.getStringProperty("timestamp");
                String content = msg.getStringProperty("content");
                WebLog webLog = new WebLog();
                webLog.setTimestamp(timestamp);
                webLog.setContent(content);
                queue.offer(webLog);
                try {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public BlockingQueue<WebLog> getQueue() {
        return queue;
    }

    private class Message {
        private Map<String, String> map;

        public Message(Map<String, String> map) {
            this.map = map;
        }

        public String getStringProperty(String propertyName) {
            return map.get(propertyName);
        }
    }
}
