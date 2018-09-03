package pers.hugh.common.practice.aop.cglib.i;

/**
 * cglib的动态代理
 * 代理对象和目标对象实现了共同的接口
 *
 * @author xzding
 * @date 2018/7/26
 */
public class MainTest {

    public static void main(String[] args) {
        TargetInterface target = new TargetClass();

        MyInterceptor myInterceptor = new MyInterceptor(target);

        TargetInterface proxy = (TargetClass) myInterceptor.createProxy();

        proxy.business();
    }
}
