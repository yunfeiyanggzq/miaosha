package com.yunfeiyang.miaosha.service.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunfeiyang.miaosha.common.utils.RedisPoolUtil;
import com.yunfeiyang.miaosha.dao.StockOrderMapper;
import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.pojo.StockOrder;
import com.yunfeiyang.miaosha.service.api.OrderService;
import com.yunfeiyang.miaosha.service.api.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @auther G.Fukang
 * @date 6/7 12:44
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

    @Override
    public int createOrder(StockOrder order) {
        return orderMapper.createOrder(order);
    }

    public int createNewOrder(int sid) throws Exception {
        Stock stock=stockService.selectByID(sid);
        if(stock.getCount()>0){
            stock.setCount(stock.getCount()-1);
            stock.setSale(stock.getSale()+1);
            stockService.addStock(stock);
        }else{
            throw new Exception("stock is not enough");
        }
        StockOrder order=new StockOrder();
        order.setCreateTime(new Date());
        order.setSid(stock.getId());
        order.setName(stock.getName());
        int res= orderMapper.createOrder(order);
        if(res==0){
            throw new Exception("failed to create order");
        }
        return  res;

    }
}
