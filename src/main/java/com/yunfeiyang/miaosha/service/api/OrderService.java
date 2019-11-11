package com.yunfeiyang.miaosha.service.api;


import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.pojo.StockOrder;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */
public interface OrderService {

    /**
     * 清空订单表
     */
    int delOrderDBBefore();

    int createOrder(StockOrder order);

    public int createNewOrder(int sid) throws Exception;
}
