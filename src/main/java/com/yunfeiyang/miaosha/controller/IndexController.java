package com.yunfeiyang.miaosha.controller;


import com.yunfeiyang.miaosha.common.StockInRedis.StockMemory;
import com.yunfeiyang.miaosha.common.limit.Limit;
import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.service.api.OrderService;
import com.yunfeiyang.miaosha.service.api.StockService;
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
public class IndexController {
    private Logger log = LoggerFactory.getLogger(IndexController.class);
    private static final String success = "SUCCESS";
    private static final String error = "ERROR";

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    /**
     * 压测前先请求该方法，初始化数据库和缓存
     */
    @RequestMapping(value = "initDBAndRedis", method = RequestMethod.POST)
    @ResponseBody
    public String initDBAndRedisBefore(HttpServletRequest request) {
        int res = 0;
        try {
            // 初始化库存信息
            res = stockService.initDBBefore();
            // 清空订单表
            res &= (orderService.delOrderDBBefore() == 0 ? 1 : 0);
            // 重置缓存
            StockMemory.clearRedis(1);

        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        if (res == 1) {
            log.info("重置数据库和缓存成功！");
        }
        return res == 1 ? success : error;
    }

    @RequestMapping(value = "addOrder", method = RequestMethod.POST)
    @ResponseBody
    public String addOrder(HttpServletRequest request,int saleId) throws Exception {
        int res=orderService.createNewOrder(saleId);
        log.info("result {}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLock", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLock(HttpServletRequest request,int saleId) throws Exception {
        int res=orderService.createNewOrderWithOptimisticLock(saleId);
        log.info("result {}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLockRedis", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLockRedis(HttpServletRequest request,int saleId) throws Exception {
        int res=orderService.CreateNewOrderWithOptimisticLockAndRedis(saleId);
        log.info("result {}",res);
        return res==1?success:error;
    }

    @RequestMapping(value = "addOrderWithLockRedisLimit", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLockRedisLimit(HttpServletRequest request,int saleId) throws Exception {
        if(!Limit.limit()) return error;
        int res=orderService.CreateNewOrderWithOptimisticLockAndRedisLimit(saleId);
        log.info("result {}",res);
        return res==1?success:error;
    }
}
