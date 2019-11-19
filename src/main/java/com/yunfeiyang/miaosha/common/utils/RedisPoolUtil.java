package com.yunfeiyang.miaosha.common.utils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */
@Slf4j
public class RedisPoolUtil {

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            //log.error("failed to set key:{} value:{} in redis,err:",key,value,e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            //log.error("failed to get key:{} from redis",key,e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
        return result;
    }

    public static Long incr (String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.incr(key);
        } catch (Exception e) {
            //log.error("failed to incr key:{}",key,e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
        return result;
    }


    public static Long decr (String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.decr(key);
        } catch (Exception e) {
            //log.error("failed to decr key:{}",key,e);
        } finally {
            RedisPool.jedisPoolClose(jedis);
        }
        return result;
    }
}
