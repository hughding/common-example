package pers.hugh.common.practice.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xzding
 * @date 2018/7/26
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk before...");
        method.invoke(this.target, args);
        System.out.println("jdk after...");
        return null;
    }
}
