package com.yunfeiyang.miaosha.dao;


import com.yunfeiyang.miaosha.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */
@Mapper
public interface StockMapper {

    @Update("update stock set count = 50, sale = 0, version = 0")
    int initDBBefore();

    @Update("update stock set count=#{count},name=#{name},sale=#{sale},version=#{version} where id=#{id}")
    int addStock(Stock stock);

    @Update("update stock set count=#{count-1},name=#{name},sale=#{sale+1},version=#{version+1} where id=#{id} and version=#{version}")
    int addStackWithOptimisticLock(Stock stock);

    @Select("select * from stock where id=#{id}")
    Stock selectByID(int id);

}
