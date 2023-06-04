package com.soda.learn.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.soda.web.mapper")
public class MainApplication {

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class);


    }
}
