package pers.hugh.common.practice.aop.jdk;

import java.lang.reflect.Proxy;

/**
 * jdk的动态代理
 * 代理对象和目标对象实现了共同的接口
 * 拦截器必须实现InvocationHanlder接口
 *
 * @author xzding
 * @date 2018/7/26
 */
public class MainTest {

    public static void main(String[] args) {
        TargetClass target = new TargetClass();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(target);

        TargetInterface proxyObject = (TargetInterface) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), myInvocationHandler);

        proxyObject.business();
    }
}
