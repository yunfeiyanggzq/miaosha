package com.yunfeiyang.miaosha.common.limit;

import com.yunfeiyang.miaosha.common.utils.RedisPool;
import com.yunfeiyang.miaosha.common.utils.ScriptUtil;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by Gu Zhiqiang on 2019-11-20
 */
public class Limit {
    private static Integer limitValue = 5;

    public static boolean limit() {

        Jedis jedis = RedisPool.getJedis();
        String script = ScriptUtil.getScript("limit.lua");
        String key = String.valueOf(System.currentTimeMillis());
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limitValue)));
        System.out.println(result);
        if ((long) result != 0) {
            return true;
        }
        return false;
    }

}
