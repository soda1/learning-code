package com.eric.learning.spring.aop;

import com.eric.learning.spring.aop.service.MyService;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author eric
 * @date 4/6/2023
 */


@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AopApplication.class);
        MyService myService = (MyService) context.getBean("myService");
        myService.testAspect("hh");
        context.close();

    }
}
