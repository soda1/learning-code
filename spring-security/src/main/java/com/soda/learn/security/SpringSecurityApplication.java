package com.soda.learn.security;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author soda
 * @date 2021/4/14
 */
@SpringBootApplication
@MapperScan(basePackages = "com.soda.security.mapper")
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class);
    }
}
