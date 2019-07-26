package com.web.demo.java8;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @CLassName AQS
 * @Author xuzhu
 * @Date 2019/7/19 上午12:40
 * @Version 1.0
 * @Description
 **/
public class AQS {

    /**
     * AQS--> abstractQueuedSynchronizer简称
     *
     */
    public static void main(String[] args) {

        AbstractQueuedSynchronizer synchronizer = new AbstractQueuedSynchronizer() {

            @Override
            protected int tryAcquireShared(int arg) {
                return super.tryAcquireShared(arg);
            }

            @Override
            protected boolean tryReleaseShared(int arg) {
                return super.tryReleaseShared(arg);
            }

        };
    }

}
