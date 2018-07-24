package com.web.demo.redis;

//https://github.com/yangliu0/DistributedLock redis 分布式锁 zookeeper 分布式锁
//redis实现分布式锁讲解blog地址：https://www.cnblogs.com/liuyang0/p/6744076.html
public class RedisLock extends Thread {

    private Service service;

    @Override
    public void run() {
        service.seckill();
    }

    public RedisLock(Service service){
        this.service = service;
    }

    public static void main(String[] args) {

        Service srv = new Service();

        for (int i = 0; i < 50; i++) {
            RedisLock redisLock = new RedisLock(srv);
            redisLock.start();
        }
    }

}
