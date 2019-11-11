package com.yunfeiyang.maiosha.common.utils;


import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */

@Component
public class RedisPool {
    private static JedisPool pool;

    private static Integer maxTotal = 300;

    private static Integer maxIdle = 100;

    private static Integer maxWait = 10000;

    private static Boolean testOnBorrow = true;

    private static String redisIP= "localhost";

    private static Integer redisPort = 7777;

    private static Integer timeout = 1000*2;

    private static void initPool(){
        JedisPoolConfig conf= new JedisPoolConfig();
        conf.setMaxIdle(maxIdle);
        conf.setTestOnBorrow(testOnBorrow);
        conf.setBlockWhenExhausted(true);
        conf.setMaxWaitMillis(maxWait);
        pool=new JedisPool(conf,redisIP,redisPort,timeout);
    }

    static{
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void jedisPoolClose(Jedis pool){
        if(pool!=null) pool.close();
    }

    public static void main(String[] args){
        System.out.println(getJedis());
    }


}

