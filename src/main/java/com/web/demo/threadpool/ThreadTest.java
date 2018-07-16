package com.web.demo.threadpool;

import java.util.concurrent.*;

public class ThreadTest {
    //推荐书籍：领域驱动设计，架构思想，
    //插件：阿里编码规约插件，MapStruct插件
    //数据库锁：https://blog.csdn.net/samjustin1/article/details/52210125
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
    //  4. 线程池类实现关系:Executor-->ExecutorService-->AbstractExecutorService-->ThreadPoolExecutor
    public static void main(String[] args) {
        //3.1 Executors中的newCachedThreadPool
        ExecutorService executorService = Executors.newCachedThreadPool();
        //   a. newCachedThreadPool，提供了两个构造方法，默认使用
        //       new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
        //           threadFactory, defaultHandler [AbortPolicy] );
        //   b. 核心线程数，最大线程数，线程空闲时间，时间单位，同步队列（用来存储等待执行的任务）
        //      拒绝处理任务时的策略AbortPolicy 丢弃任务并抛出异常
        ThreadPoolExecutor executorService1 = new ThreadPoolExecutor(5,10,2000,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(5),new MyRejectPolicy());
        for (int i=0;i<20;i++){
            //executorService.execute(new ThreadHandle(String.valueOf(i)));
            executorService1.execute(new ThreadHandle(String.valueOf(i)));
            //当核心线程满了，则将线程缓存到队列中，如果队列满了，就创建新线程去处理
            System.out.println("线程中的数量："+ executorService1.getPoolSize()+
                    ",队列中等待的线程数："+executorService1.getQueue().size()+",已执行完的线程数："+executorService1.getCompletedTaskCount());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println(executorService1.getCompletedTaskCount());
    }
}
