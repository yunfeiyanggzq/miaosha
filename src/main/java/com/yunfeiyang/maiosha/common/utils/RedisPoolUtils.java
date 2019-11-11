package com.yunfeiyang.maiosha.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */

public class RedisPoolUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedisPoolUtils.class.getName());

    public void set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            logger.debug("set key %s value %s err %v", key, value, e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.debug("get key %s  err %v", key, e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
        return result;
    }
}
