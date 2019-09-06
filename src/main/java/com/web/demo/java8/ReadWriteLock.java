package com.web.demo.java8;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @CLassName ReadWriteLock
 * @Author xuzhu
 * @Date 2019/9/5 下午10:39
 * @Version 1.0
 * @Description
 **/
public class ReadWriteLock {

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static Map<String, Object> map = Maps.newHashMap();

    private Lock readLock = reentrantReadWriteLock.readLock();

    private Lock writeLock = reentrantReadWriteLock.writeLock();

    private volatile Boolean flag = true;

    /**
     * 锁降级
     * @param key
     * @param value
     * @return
     */
    public Object lockDegradation(String key, Object value){

        readLock.lock();
        if (flag) {
            System.out.println(Thread.currentThread().getName() + " 获取到读锁。。。");
            readLock.unlock();

            writeLock.lock();
            map.put(key, value);
            readLock.lock();
        }
        System.out.println(Thread.currentThread().getName() + " sleep...");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println("sleep error...");
        }
        writeLock.unlock();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println("sleep error...");
        }
        System.out.println(Thread.currentThread().getName() + " 获取到值：" + map.get(key));
        readLock.unlock();
        return map.get(key);
    }

    /**
     * 读锁
     * @param key
     * @return
     */
    public Object get(String key) {

        Object obj = new Object();

        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "正在读取数据。。。");
            Thread.sleep(3000);
            obj = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取到数据。。。" );
        } catch (Exception e) {
            System.out.println("sleep error...");
        }finally {
            readLock.unlock();
        }
        return obj;
    }

    /**
     * 写锁
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "正在写数据。。。");
            Thread.sleep(3000);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写完数据。。。");
        } catch (InterruptedException e) {
            System.out.println("sleep error....");
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {

        ReadWriteLock readWriteLock = new ReadWriteLock();
        map.put("key1", "value");
        new Thread(() -> {
            readWriteLock.lockDegradation("key1", "value1");
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("sleep error...");
        }
        new Thread(() -> {
            readWriteLock.lockDegradation("key1", "value2");
        }).start();
        //读锁和写锁互斥
        /*new Thread(() -> {
            readWriteLock.put("key1", "value1");
        }).start();
        new Thread(() -> {
            readWriteLock.get("key1");
        }).start();*/
       /*ExecutorService executorService = Executors.newCachedThreadPool();
        // 写数据 写锁互斥
        executorService.execute(() -> {
            readWriteLock.put("key1", "value1");
        });
        executorService.execute(() -> {
            readWriteLock.put("key2", "value2");
        });
        executorService.execute(() -> {
            readWriteLock.put("key3", "value3");
        });

        //读数据 读锁不互斥
        executorService.execute(() -> {
            readWriteLock.get("key1");
        });
        executorService.execute(() -> {
            readWriteLock.get("key2");
        });
        executorService.execute(() -> {
            readWriteLock.get("key3");
        });*/

    }


}
