package com.web.demo.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
    //线程池讲解参考地址：https://www.cnblogs.com/dolphin0520/p/3932921.html
    //线程池构造方法四个构造方法：
    //  1. ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
    //     BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler);
    //  2. 参数说明：
    //      核心线程数，最大线程数，线程空闲时间，时间单位，
    //      工作队列(用来存储等待执行的任务)[ArrayBlockingQueue;LinkedBlockingQueue;SynchronousQueue]，
    //      线程工厂，用于创建线程，
    //      拒绝处理任务时的策略[{
    //          ThreadPoolExecutor.AbortPolicy:          丢弃任务并抛出RejectedExecutionException异常,
    //          ThreadPoolExecutor.DiscardPolicy：       也是丢弃任务，但是不抛出异常,
    //          ThreadPoolExecutor.DiscardOldestPolicy： 丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）,
    //          ThreadPoolExecutor.CallerRunsPolicy：    由调用线程处理该任务
    //      }]
    //  3. java提供的线程池包括（Executors）：newCachedThreadPool,newFixedThreadPool,newSingleThreadExecutor,newScheduledThreadPool
    public static void main(String[] args) {
        //3.1 Executors中的newCachedThreadPool
        ExecutorService executorService = Executors.newCachedThreadPool();
        //   a. newCachedThreadPool，提供了两个构造方法，默认使用
        //       new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
        //           threadFactory, defaultHandler [AbortPolicy] );
        //   b. 核心线程数，最大线程数，线程空闲时间，时间单位，同步队列（用来存储等待执行的任务）
        //      拒绝处理任务时的策略AbortPolicy 丢弃任务并抛出异常
        for (int i=0;i<5;i++){
            executorService.execute(new ThreadHandle(String.valueOf(i)));
        }
    }
}
