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
                cwl.countDown();
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "执行完成");
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
        cwl.await();
        System.out.println("say hello!");
        countDownLatch.countDown();
    }
}
