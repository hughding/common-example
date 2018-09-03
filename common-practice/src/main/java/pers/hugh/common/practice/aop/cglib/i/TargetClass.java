package pers.hugh.common.practice.aop.cglib.i;

/**
 * @author xzding
 * @date 2018/7/26
 */
public class TargetClass implements TargetInterface {
    @Override
    public void business() {
        System.out.println("jdk target do business...");
    }
}
