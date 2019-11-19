package com.yunfeiyang.miaosha.service.api;


import com.yunfeiyang.miaosha.pojo.Stock;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */
public interface OrderService {

    int delOrderDBBefore();

    int consumerFromKafaka(Stock stock)throws  Exception;

    int createNewOrder(int sid) throws Exception;

    int createNewOrderWithOptimisticLock(int sid) throws Exception;

    int CreateNewOrderWithOptimisticLockAndRedis(int sid)throws  Exception;

    int CreateNewOrderWithOptimisticLockAndRedisLimit(int sid) throws Exception;

    void CreateNewOrderWithOptimisticLockAndRedisLimitKafka(int sid)throws Exception;
}
