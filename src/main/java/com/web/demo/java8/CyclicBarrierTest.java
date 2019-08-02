package com.web.demo.java8;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @CLassName CyclicBarrierTest
 * @Author xuzhu
 * @Date 2019/7/23 上午8:55
 * @Version 1.0
 * @Description
 **/
public class CyclicBarrierTest {

    // CyclicBarrier中reset()方法的使用：https://blog.csdn.net/J080624/article/details/85261930

    public static CountDownLatch cwl;

    public CyclicBarrierTest(int count) {
        cwl = new CountDownLatch(count);
    }

    public class Work extends Thread {

        private CyclicBarrier cyclicBarrier;

        private CountDownLatch countDownLatch;

        public Work(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch) {
            this.cyclicBarrier = cyclicBarrier;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "正在等待其他线程");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "正在执行");
                Thread.sleep(1000);
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "执行完成");
                cwl.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int count = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest(3);

        for (int i = 0; i < 3; i++) {
            System.out.println("创建工作线程" + i);
            Work work = cyclicBarrierTest.new Work(cyclicBarrier, countDownLatch);
            work.start();
        }
        countDownLatch.countDown();
        System.out.println("say hello!");
        cwl.await();
        System.out.println("-------");
        cyclicBarrier.reset();
        for (int i = 0; i < 3; i++) {
            System.out.println("创建新的工作线程" + (i + 3));
            Work work = cyclicBarrierTest.new Work(cyclicBarrier, countDownLatch);
            work.start();
        }
    }
}
