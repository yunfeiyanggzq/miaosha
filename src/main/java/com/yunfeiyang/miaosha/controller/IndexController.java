package com.yunfeiyang.miaosha.controller;


import com.yunfeiyang.miaosha.common.StockInRedis.StockMemory;
import com.yunfeiyang.miaosha.common.limit.Limit;
import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.service.api.OrderService;
import com.yunfeiyang.miaosha.service.api.StockService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */


@Controller
@RequestMapping(value = "/")
@Slf4j
public class IndexController {
    private static final String success = "SUCCESS";
    private static final String error = "ERROR";

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "initDBAndRedis", method = RequestMethod.POST)
    @ResponseBody
    public String initDBAndRedisBefore(HttpServletRequest request) {
        int res = 0;
        try {
            res = stockService.initDBBefore();
            res &= (orderService.delOrderDBBefore() == 0 ? 1 : 0);
            StockMemory.clearRedis(1);

        } catch (Exception e) {
            //log.error("failed to init mysql and redis before run", e);
        }
        if (res == 1) {
            //log.info("success to init mysql and redis before run！");
        }
        return res == 1 ? success : error;
    }

    @RequestMapping(value = "addOrder", method = RequestMethod.POST)
    @ResponseBody
    public String addOrder(HttpServletRequest request,int saleId) throws Exception {
        int res=orderService.createNewOrder(saleId);
        //log.info("add a order result:{}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLock", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLock(HttpServletRequest request,int saleId) throws Exception {
        int res=orderService.createNewOrderWithOptimisticLock(saleId);
        //log.info("add a order with optimistic lock resylt:{}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLockRedis", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLockRedis(HttpServletRequest request,int saleId) throws Exception {
        int res=orderService.CreateNewOrderWithOptimisticLockAndRedis(saleId);
        //log.info("add a order with optimistic lock and redis result:{}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLockRedisLimit", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLockRedisLimit(HttpServletRequest request,int saleId) throws Exception {
        if(!Limit.limit()) return error;
        int res=orderService.CreateNewOrderWithOptimisticLockAndRedisLimit(saleId);
        //log.info("add a order with optimistic lock , redis and limit result:{}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLockRedisLimitKafka", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLockRedisLimitKafka(HttpServletRequest request,int saleId) throws Exception {
        if(!Limit.limit()) return error;
        orderService.CreateNewOrderWithOptimisticLockAndRedisLimitKafka(saleId);
        //log.info("add the request to a kafka queue");
        return "add into a kafka queue";
    }

}
