package com.yunfeiyang.miaosha.common.limit;

import com.yunfeiyang.miaosha.common.utils.RedisPool;
import com.yunfeiyang.miaosha.common.utils.ScriptUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by Gu Zhiqiang on 2019-11-20
 */
@Slf4j
public class Limit {
    private static Integer limitValue = 5;
    private static String script;

    static {
        try {
            script = ScriptUtil.getScript("limit.lua");
        } catch (IOException e) {
            log.error("failed to read lua script",e);
            e.printStackTrace();
        }
    }

    public static boolean limit() {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            String key = String.valueOf(System.currentTimeMillis() / 1000);
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limitValue)));
            if ((long) result != 0) {
                log.info("success to get the limit check pass");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        log.info("failed to get the limit check pass");
        return false;
    }


}
