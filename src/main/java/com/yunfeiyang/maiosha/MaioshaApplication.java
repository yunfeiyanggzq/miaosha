package com.yunfeiyang.maiosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
@ComponentScan
public class MaioshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaioshaApplication.class, args);
    }

}
