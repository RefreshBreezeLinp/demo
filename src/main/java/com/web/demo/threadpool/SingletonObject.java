package com.web.demo.threadpool;

/**
 * @CLassName SingletonObject
 * @Author
 * @Date 2019/3/20 上午8:55
 * @Version 1.0
 * @Description
 **/
public class SingletonObject {

    private SingletonObject(){}

    private static SingletonObject singletonObject = null;

    public static SingletonObject getInstance(){
        try {
            if (singletonObject != null) {
            }
            else {
                Thread.sleep(300);
                synchronized (SingletonObject.class) {
                    if (singletonObject == null)
                        singletonObject = new SingletonObject();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return singletonObject;
    }

}
class Test extends Thread {

    @Override
    public void run() {
        System.out.println(SingletonObject.getInstance().hashCode());
    }

}
class Test2 extends Thread {
    @Override
    public void run() {
        System.out.println(SingletonObject.getInstance().hashCode());
    }
}
class Test1 extends Thread {
    @Override
    public void run() {
        System.out.println(SingletonObject.getInstance().hashCode());
    }
}
class Test3 extends Thread {
    @Override
    public void run() {
        System.out.println(SingletonObject.getInstance().hashCode());
    }
}
class SingObjectThread {

    public static void main(String[] args) {
        Test t1 = new Test();
        Test1 t2 = new Test1();
        Test2 t3 = new Test2();
        Test3 t4 = new Test3();
        t2.start();
        t1.start();
        t3.start();
        t4.start();
    }
}