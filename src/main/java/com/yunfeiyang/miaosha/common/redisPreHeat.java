package com.yunfeiyang.miaosha.common;

import com.yunfeiyang.miaosha.common.constant.Constants;
import com.yunfeiyang.miaosha.common.utils.RedisPoolUtil;
import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.service.api.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Gu Zhiqiang on 2019-11-19
 */
@Slf4j
@Component
public class redisPreHeat implements ApplicationRunner {
    @Autowired
    private  StockService stockService;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Stock stock= stockService.selectByID(1);
        int sid=stock.getId();
        RedisPoolUtil.set(Constants.STOCK_COUNT+sid,String.valueOf(stock.getCount()));
        RedisPoolUtil.set(Constants.STOCK_SALE+sid,String.valueOf(stock.getSale()));
        RedisPoolUtil.set(Constants.STOCK_VERSION+sid,String.valueOf(stock.getVersion()));
    }
}
