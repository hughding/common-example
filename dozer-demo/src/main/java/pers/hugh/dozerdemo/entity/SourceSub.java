package pers.hugh.dozerdemo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/3/28</pre>
 */
@Data
public class SourceSub {
    private int subA;
    private String subB;
    private List<String> list;
}
