package pers.hugh.stormdemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/8</pre>
 */
@Data
public class WebLog implements Serializable{
    private String timestamp;
    private String content;
}
