package com.eric.learning.spring.autowire;

import com.eric.learning.spring.autowire.configureation.AutowireBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author eric
 * @date 4/6/2023
 */

@SpringBootApplication
public class AutowireApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutowireApplication.class);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        System.out.println("fafa");
        while (true) {

        }
    }
}
