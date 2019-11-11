package com.yunfeiyang.maiosha.service.impl;

import com.yunfeiyang.maiosha.dao.StockMapper;
import com.yunfeiyang.maiosha.service.api.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gu Zhiqiang on 2019-11-15
 */
@Service
public class StockService implements Stock {
    @Autowired
    public StockMapper stock;

    @Override
    public int initStockDBBefore() {
        return stock.initStockDBBefore();
    }
}
