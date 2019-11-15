package com.yunfeiyang.miaosha.service.impl;


import com.yunfeiyang.miaosha.common.StockInRedis.StockMemory;
import com.yunfeiyang.miaosha.common.constant.Constants;
import com.yunfeiyang.miaosha.common.utils.RedisPoolUtil;
import com.yunfeiyang.miaosha.dao.StockOrderMapper;
import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.pojo.StockOrder;
import com.yunfeiyang.miaosha.service.api.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(value = "OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StockOrderMapper orderMapper;

    @Autowired
    private StockServiceImpl stockService;

    @Override
    public int delOrderDBBefore() {
        return orderMapper.delOrderDBBefore();
    }

    public int createOrder(StockOrder order) {
        return orderMapper.createOrder(order);
    }

    public int createNewOrder(int sid) throws Exception {
       Stock stock=checkSale(sid);
       saleStock(stock);
       return createOrder(stock);
    }

    public int createNewOrderWithOptimisticLock(int sid) throws Exception {
        Stock stock=checkSale(sid);
        saleStockWithOptimisticLock(stock);
        return createOrder(stock);

    }

    @Override
    public int CreateNewOrderWithOptimisticLockAndRedis(int sid) throws Exception {
        Stock stock=checkSaleFromRedis(sid);
        saleStockWithOptimisticLockRedis(stock);
        return createOrder(stock);
    }

    @Override
    public int CreateNewOrderWithOptimisticLockAndRedisLimit(int sid) throws Exception {
        Stock stock=checkSaleFromRedis(sid);
        saleStockWithOptimisticLockRedis(stock);
        return createOrder(stock);
    }

    @Override
    public void consumerFromKafaka() {

    }

    public Stock checkSale(int sid) throws Exception {
        Stock stock=stockService.selectByID(sid);
        if(stock.getCount()<1){
            throw  new Exception("stock is 0");
        }
        return stock;
    }

    public Stock checkSaleFromRedis(int sid) throws Exception {
        int count=Integer.parseInt(RedisPoolUtil.get(Constants.STOCK_COUNT+sid));
        if(count<1){
            throw  new Exception("stock is 0");
        }
        Stock stock=new Stock();
        stock.setCount(count);
        stock.setVersion(Integer.parseInt(RedisPoolUtil.get(Constants.STOCK_VERSION+sid)));
        stock.setSale(Integer.parseInt(RedisPoolUtil.get(Constants.STOCK_SALE+sid)));
        stock.setName("huawei");
        stock.setId(sid);
        return stock;

    }

    public int saleStock(Stock stock){
        stock.setCount(stock.getCount()-1);
        stock.setSale(stock.getSale()+1);
        return stockService.addStock(stock);
    }

    public int saleStockWithOptimisticLock(Stock stock) throws Exception {
        int res=stockService.addStackWithOptimisticLock(stock);
        if(res==0){
            throw  new Exception("drop the request because the optimistic lock");
        }
        return res;
    }
    public int saleStockWithOptimisticLockRedis(Stock stock) throws Exception {
        int res=stockService.addStackWithOptimisticLock(stock);
        if(res==0){
            throw  new Exception("drop the request because the optimistic lock");
        }
        StockMemory.redisLoadFromDB(stock);
        return res;
    }

    public int createOrder(Stock stock) throws Exception {
        StockOrder order=new StockOrder();
        order.setCreateTime(new Date());
        order.setSid(stock.getId());
        order.setName(stock.getName());
        int res= orderMapper.createOrder(order);
        if(res==0){
            throw new Exception("failed to create order");
        }
        return res;
    }
}
