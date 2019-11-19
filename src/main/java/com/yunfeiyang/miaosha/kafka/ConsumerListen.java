package com.yunfeiyang.miaosha.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunfeiyang.miaosha.pojo.Stock;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.yunfeiyang.miaosha.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Gu Zhiqiang on 2019-11-20
 */


@Component
public class ConsumerListen {
    private Gson gson=new GsonBuilder().create();

    @Autowired
    private OrderService service;

    @KafkaListener(topics = "miaosha")
    public void listen(ConsumerRecord<String,String> record) throws Exception {
        Optional<?> KafkaMessage=Optional.ofNullable(record.value());
        String message=(String)KafkaMessage.get();
        Stock stock=gson.fromJson(message,Stock.class);
        service.consumerFromKafaka(stock);
    }
}
