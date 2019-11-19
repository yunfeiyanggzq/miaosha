package com.yunfeiyang.miaosha.dao;


import com.yunfeiyang.miaosha.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Gu Zhiqiang on 2019-11-11
 */
@Mapper
public interface StockMapper {

    @Update("UPDATE stock SET count = 1000, sale = 0, version = 0")
    int initDBBefore();

    @Select("SELECT * FROM stock WHERE id = #{id, jdbcType = INTEGER}")
    Stock selectByID(@Param("id") int id);

    @Update("UPDATE stock SET count = #{count, jdbcType = INTEGER}, name = #{name, jdbcType = VARCHAR}, " +
            "sale = #{sale,jdbcType = INTEGER},version = #{version,jdbcType = INTEGER} " +
            "WHERE id = #{id, jdbcType = INTEGER}")
    int addStock(Stock stock);

    // optimistic lock
    @Update("UPDATE stock SET count = count - 1, sale = sale + 1, version = version + 1 WHERE " +
            "id = #{id, jdbcType = INTEGER} AND version = #{version, jdbcType = INTEGER}")
    int addStackWithOptimisticLock(Stock stock);
}
