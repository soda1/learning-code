package com.soda.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列配置
 */
@Configuration
public class DelayQueueConfig {

    @Bean("delayQueue")
    public Queue delayQueue() {

        Map map = new HashMap<String, Object>();
        //设置消息过期时间
        map.put("x-message-ttl", 10000);
        //设置死信路由
        map.put("x-dead-letter-exchange", "soda.delayExchange");
        //设置死信路由key
        map.put("x-dead-letter-routing-key", "soda.delayDeadKey");
        return new Queue("soda.delayQueue", true, false, false, map);
    }

    //死信队列
    @Bean("delayDeadQueue")
    public Queue delayDeadQueue() {
        return new Queue("soda.deadDelayQueue", true);
    }

    //路由
    @Bean("delayExchange")
    public Exchange delayExchange() {
        return new DirectExchange("soda.delayExchange", true, false);
    }

    //延迟队列
    @Bean
    public Binding bindExchange(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") Exchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("soda.delayKey").noargs();
    }

    //延迟死信队列
    @Bean
    public Binding bindExchange1(@Qualifier("delayDeadQueue") Queue queue, @Qualifier("delayExchange") Exchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("soda.delayDeadKey").noargs();
    }
}
