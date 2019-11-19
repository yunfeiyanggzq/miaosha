package com.yunfeiyang.miaosha.service.api;


import com.yunfeiyang.miaosha.pojo.Stock;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */
public interface StockService {

    int initDBBefore();

    int addStock(Stock stock);

    int addStackWithOptimisticLock(Stock stock);

    Stock selectByID(int id);
}
