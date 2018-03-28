package pers.hugh.dozerdemo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/3/28</pre>
 */
@Data
public class Source {
    private int a;
    private String b;
    private List<String> list;
    private List<SourceSub> list2;
}
