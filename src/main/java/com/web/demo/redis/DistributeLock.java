package com.web.demo.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

public class DistributeLock {

    private  final JedisPool jedisPool;

    public DistributeLock (JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String lockWithTimeOut(String localName, long acquireTimeOut, long timeOut){

        Jedis conn = null;
        String retIdentifier = null;
        try {
            //获取链接
            conn = jedisPool.getResource();
            //随机生成一个value
            String identifier = UUID.randomUUID().toString();
            //锁名，即key值
            String lockKey = "lock:" + localName;
            //超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int) ((timeOut)/1000);
            //获取锁的超时时间，超过这个时间就放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeOut;
            while (System.currentTimeMillis()<end){
                if (conn.setnx(lockKey,identifier)==1){
                    conn.expire(lockKey,lockExpire);
                    retIdentifier = identifier;
                    //返回value值，用于释放锁时间确认
                    return retIdentifier;
                }
                //返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey)==-1){
                    conn.expire(lockKey,lockExpire);
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e){
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return retIdentifier;
    }

    public boolean releaseLock(String localName,String identifier){
        Jedis conn = null;
        String lockKey = "lock:" +localName;
        boolean retflag = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                //监视key，准备开启事务
                conn.watch(lockKey);
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> list = transaction.exec();
                    if (list==null){
                        continue;
                    }
                    retflag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn!=null){
                conn.close();
            }
        }
        return retflag;
    }
}
