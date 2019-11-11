package com.yunfeiyang.maiosha.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */
public class ScriptUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedisPoolUtils.class.getName());

    public static String getScript(String path) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream input = ScriptUtils.class.getClassLoader().getResourceAsStream(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str).append(System.lineSeparator());
            }
        } catch (Exception e) {
            logger.error("read lua script err %v", e);
        }
        return stringBuffer.toString();
    }
}
