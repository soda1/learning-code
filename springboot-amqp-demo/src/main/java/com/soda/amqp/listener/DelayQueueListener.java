package com.soda.amqp.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DelayQueueListener {

    /**
     * delayDeadQueue 消息消费
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {"soda.deadDelayQueue"})
    public void delayDeadQueue(Message message, Channel channel) {

        System.out.println("处理延时消息" + new String(message.getBody()) );
    }

}
