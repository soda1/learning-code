package com.soda.learn.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AMQPConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //定义一个queue
    @Bean("consumerQueue")
    public Queue getQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "soda.deadExchange");
        args.put("x-dead-letter-routing-key", "soda.deadLetter");
        return new Queue("soda.queue", true, false, false, args);
    }

    //定义一个exchange
    @Bean("consumerExchange")
    public Exchange getChange() {
        return new DirectExchange("soda.exchange", true, false);
    }

    @Bean
    public Binding binding(@Qualifier("consumerQueue") Queue queue, @Qualifier("consumerExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("soda.test").noargs();
    }

    //创建一个死信交换机
    @Bean("deadExchange")
    Exchange deadExchange() {
        return new DirectExchange("soda.deadExchange", true, false);
    }

    //创建一个死信队列
    @Bean("deadQueue")
    Queue deadQueue() {
        return new Queue("soda.deadQueue", true);
    }

    //交换机与队列绑定
    @Bean
    public Binding deadBinding(@Qualifier("deadQueue")Queue queue, @Qualifier("deadExchange")Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("soda.deadLetter").noargs();
    }






    @PostConstruct
    public void init() {

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            {
                if (ack) {
                    System.out.println("消息发送成功");
                } else {
                    System.out.println(cause);
                }
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("消息无法到达路由" + message.getBody());
        });
    }

}
