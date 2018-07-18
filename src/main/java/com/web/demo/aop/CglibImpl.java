package com.web.demo.aop;

public class CglibImpl implements CglibInterface {
    @Override
    public void say(String str) {
        System.out.println("hello "+str);
    }
}
