package com.web.demo.java8;

import java.util.concurrent.CountDownLatch;

/**
 * @CLassName CountDownLatchTest
 * @Author xuzhu
 * @Date 2019/7/18 上午12:29
 * @Version 1.0
 * @Description
 **/
public class CountDownLatchTest {

    private static final Integer SIZE = 2;

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run1...");
                countDownLatch.countDown();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run2...");
                countDownLatch.countDown();
            }
        });

        t2.start();
        t1.start();
        countDownLatch.await();
        System.out.println("end");
    }

}
