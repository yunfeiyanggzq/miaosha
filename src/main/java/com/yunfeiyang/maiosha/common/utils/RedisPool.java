package com.yunfeiyang.maiosha.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    private static String redisIP = "120.92.122.136";

    private static Integer redisPort = 7777;

    private static Integer timeout = 1000 * 2;

    private static String password = "Gzq123456";

    private static final Logger logger = LoggerFactory.getLogger(RedisPoolUtils.class.getName());

    private static void initPool() {
        JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxIdle(maxIdle);
        conf.setTestOnBorrow(testOnBorrow);
        conf.setBlockWhenExhausted(true);
        conf.setMaxWaitMillis(maxWait);
        pool = new JedisPool(conf, redisIP, redisPort, timeout, password);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void jedisPoolClose(Jedis pool) {
        if (pool != null) pool.close();
    }

    public static void main(String[] args) {
        Jedis redis = getJedis();
        String a = redis.set("name", "zhangsan");
        logger.debug("get key {} successfully", "name");
        String script = ScriptUtils.getScript("limit.lua");
        logger.debug("get script {} successfully", script);



        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://120.92.122.136:3306/miaosha";
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 打开链接

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,"root","123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("连接数据库...");
    }
}

