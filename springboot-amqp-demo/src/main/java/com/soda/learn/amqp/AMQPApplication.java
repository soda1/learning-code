package com.soda.learn.amqp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class AMQPApplication {
    public static void main(String[] args) {
        SpringApplication.run(AMQPApplication.class);
    }
}
