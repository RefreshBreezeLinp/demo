package com.web.demo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;

public class CglibTest implements MethodInterceptor {

    //Spring AOP讲解：https://www.cnblogs.com/zhaozihan/p/5953063.html

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        before();
        Object result = methodInvocation.proceed();
        after();
//        ProxyFactoryBean
        return result;
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new CglibImpl());
        proxyFactory.addAdvice(new CglibTest());
        CglibInterface cglibInterface = (CglibInterface) proxyFactory.getProxy();
        cglibInterface.say("world");
    }
}
