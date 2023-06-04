package com.eric.learning.spring.beanLife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author eric
 * @date 2/20/2023
 */

@SpringBootApplication
public class BeanLifeApplication {



    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(BeanLifeApplication.class, args);
        //AwareInterfaceImpl springBean = (AwareInterfaceImpl) applicationContext.getBean("beanInstance");
        applicationContext.close();

    }
}
