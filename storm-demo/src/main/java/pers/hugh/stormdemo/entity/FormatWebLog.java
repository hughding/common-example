package pers.hugh.stormdemo.entity;

import lombok.Data;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/8</pre>
 */
@Data
public class FormatWebLog {
    private long timeStamp;
    private String departureCity;
    private String arrivalCity;
}
