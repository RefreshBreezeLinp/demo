package com.web.demo.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @CLassName ThreadPoolDemo
 * @Author li.np
 * @Date 2018/8/21 下午7:17
 * @Version 1.0
 * @Description
 **/
public class ThreadPoolDemo {


    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()) {

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                System.out.println(t.getMessage());
            }
        };

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("task1");
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("task2");
            }
        });
    }

}
