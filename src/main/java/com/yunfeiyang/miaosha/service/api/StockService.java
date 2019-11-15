package com.yunfeiyang.miaosha.service.api;


import com.yunfeiyang.miaosha.pojo.Stock;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */
public interface StockService {

    int initDBBefore();

    int addStock(Stock stock);

    int addStackWithOptimisticLock(Stock stock);

    Stock selectByID(int id);
}
