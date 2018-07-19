package com.web.demo.aop;

import sun.applet.Main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//java动态代理 主要包括两个内容：InvocationHandler接口和Proxy类
//  1. 实现InvocationHandler接口
//        invoke(Object proxy, Method method, Object[] args)
//          参数说明：proxy 所代理的对象，method 所要调用对象的某个方法的method对象，args 所要调用对象的某个方法的参数
//  2. Proxy用于创建一个代理对象 newProxyInstance方法
//        newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
//          参数说明：loader 一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
//                   interfaces 一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了
//                   h 一个InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上
public class JavaProxy implements InvocationHandler {

    private Object target;

    public JavaProxy(Object target){
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        method.invoke(target,args);
        System.out.println("after");
        return null;
    }

    public static void main(String[] args) {
        CglibInterface cglibInterface = new CglibImpl();
        JavaProxy pro = new JavaProxy(cglibInterface);
        CglibInterface proxy = (CglibInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),CglibImpl.class.getInterfaces(),pro);
        proxy.say("word");
    }
}
