package com.web.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Service {

    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(8);
        config.setMaxWaitMillis(1000 * 1000);
        //在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config,"localhost",6379,3000);
    }

    DistributeLock distributeLock = new DistributeLock(pool);

    int n = 500;

    public  void seckill(){
        String indentifier = distributeLock.lockWithTimeOut("resource",5000,1000);
        System.out.println(Thread.currentThread().getName()+"获得了锁");
        System.out.println(--n);
        distributeLock.releaseLock("resource",indentifier);
    }

}
