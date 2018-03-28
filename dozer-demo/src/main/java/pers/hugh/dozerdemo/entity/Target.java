package pers.hugh.dozerdemo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/3/28</pre>
 */
@Data
public class Target {
    private int a;
    private String b2;
    private List<String> list;
    private List<TargetSub> list2;
}
