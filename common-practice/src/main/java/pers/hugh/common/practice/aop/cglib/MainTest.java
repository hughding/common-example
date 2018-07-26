package pers.hugh.common.practice.aop.cglib;

/**
 * cglib的动态代理
 * 代理对象是目标对象的子类
 * 拦截器必须实现MethodInterceptor接口
 * hibernate中session.load采用的是cglib实现的
 *
 * @author xzding
 * @date 2018/7/26
 */
public class MainTest {

    public static void main(String[] args) {
        TargetClass target = new TargetClass();
        MyInterceptor myInterceptor = new MyInterceptor(target);

        TargetClass proxy = (TargetClass) myInterceptor.createProxy();

        proxy.business();
    }
}
