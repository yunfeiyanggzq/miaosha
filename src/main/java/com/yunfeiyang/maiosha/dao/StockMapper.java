package com.yunfeiyang.maiosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * Created by Gu Zhiqiang on 2019-11-14
 */

@Mapper
@Component
public interface StockMapper {

    @Update("UPDATE stock SET id=0,version=0,count=50")
    int initStockDBBefore();
}
