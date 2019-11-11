package com.yunfeiyang.maiosha.controller;

import com.yunfeiyang.maiosha.service.impl.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */
@Controller
@RequestMapping(value="/")
public class App {

    @Autowired
    private StockService stockService;

    @RequestMapping(value="initApplication", method= RequestMethod.POST)
    public void init(){
        stockService.initStockDBBefore();
    }

}
