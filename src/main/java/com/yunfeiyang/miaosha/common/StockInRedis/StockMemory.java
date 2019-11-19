package com.yunfeiyang.miaosha.common.StockInRedis;

import com.yunfeiyang.miaosha.common.constant.Constants;
import com.yunfeiyang.miaosha.common.utils.RedisPool;
import com.yunfeiyang.miaosha.common.utils.RedisPoolUtil;
import com.yunfeiyang.miaosha.pojo.Stock;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Created by Gu Zhiqiang on 2019-11-17
 */
@Slf4j
public class StockMemory {
    public static final Logger log = LoggerFactory.getLogger(StockMemory.class);

    // clearRedis reset the redis
    public static void clearRedis(int sid) {
        Jedis redis=null;
        try {
            redis = RedisPool.getJedis();
            Transaction tx = redis.multi();
            RedisPoolUtil.set(Constants.STOCK_SALE + sid, String.valueOf(0));
            RedisPoolUtil.set(Constants.STOCK_VERSION + sid, String.valueOf(0));
            RedisPoolUtil.set(Constants.STOCK_COUNT + sid, String.valueOf(1000));
            tx.exec();
        }catch (Exception e){
            //log.error("failed to reset redis,err:",e);
        }finally {
            RedisPool.jedisPoolClose(redis);
        }
    }

    // redisLoadFromDB make redis and mysql syc
    public static void redisLoadFromDB(Stock stock){
        Jedis redis=null;
        try {
            redis = RedisPool.getJedis();
            Transaction tx = redis.multi();
            RedisPoolUtil.incr(Constants.STOCK_SALE + stock.getId());
            RedisPoolUtil.incr(Constants.STOCK_VERSION + stock.getId());
            RedisPoolUtil.decr(Constants.STOCK_COUNT + stock.getId());
            tx.exec();
        }catch (Exception e){
            //log.error("failed to make redis and mysql syc,err:",e);
        }finally {
            RedisPool.jedisPoolClose(redis);
        }
    }
}
