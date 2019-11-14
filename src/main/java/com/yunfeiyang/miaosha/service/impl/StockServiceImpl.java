package com.yunfeiyang.miaosha.service.impl;

import com.yunfeiyang.miaosha.dao.StockMapper;
import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.service.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public int initDBBefore() {
        return stockMapper.initDBBefore();
    }

    @Override
    public int addStock(Stock stock) {
        return stockMapper.addStock(stock);
    }

    @Override
    public int addStackWithOptimisticLock(Stock stock) {
        return stockMapper.addStackWithOptimisticLock(stock);
    }

    @Override
    public Stock selectByID(int id) {
        return stockMapper.selectByID(id);
    }
}
