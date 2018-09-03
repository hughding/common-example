package pers.hugh.common.practice.aop.cglib.i;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xzding
 * @date 2018/7/26
 */
public class MyInterceptor implements MethodInterceptor {

    private Object target;

    public MyInterceptor(Object target) {
        this.target = target;
    }

    public Object createProxy(){
        Enhancer enhancer = new Enhancer();
        //回调此拦截器
        enhancer.setCallback(this);
        //设置代理对象的父类
        enhancer.setSuperclass(this.target.getClass());
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib before...");
        method.invoke(target, objects);
        System.out.println("cglib after...");
        return null;
    }
}
