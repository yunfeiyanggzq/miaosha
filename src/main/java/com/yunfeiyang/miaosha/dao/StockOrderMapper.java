package com.yunfeiyang.miaosha.dao;

import com.yunfeiyang.miaosha.pojo.Stock;
import com.yunfeiyang.miaosha.pojo.StockOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */
@Mapper()
public interface StockOrderMapper {

    @Update("truncate table stock_order")
    int delOrderDBBefore();

    @Insert("insert into stock_order values(#{id},#{sid},#{name},#{createTime})")
    int createOrder(StockOrder order);
}
