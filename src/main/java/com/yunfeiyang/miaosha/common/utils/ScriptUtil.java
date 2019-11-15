package com.yunfeiyang.miaosha.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */
public class ScriptUtil {

    // read lua
    public static String getScript(String path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = ScriptUtil.class.getClassLoader().getResourceAsStream(path);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str).append(System.lineSeparator());
            }
            inputStream.close();
        return stringBuilder.toString();
    }
}
