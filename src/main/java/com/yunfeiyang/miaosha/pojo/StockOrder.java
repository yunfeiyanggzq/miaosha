package com.yunfeiyang.miaosha.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Gu Zhiqiang on 2019-11-16
 */

public class StockOrder {

    private Integer id;

    private Integer sid;

    private String name;

    private Date createTime;

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
