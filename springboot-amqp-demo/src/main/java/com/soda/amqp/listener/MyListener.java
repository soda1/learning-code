package com.soda.amqp.listener;


import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class MyListener {

/*    @RabbitListener(queues = {"soda.queue"})
    public void receive1(Object object, Message message){

        System.out.println(message == object);
        //收到消息：class org.springframework.amqp.core.Message
    }*/


    @RabbitListener(queues = {"soda.queue"})
    public void receive2(Message message, Channel channel){
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
//            int i = 1/0;
            System.out.println("收到消息----" + message.getBody().toString());
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            e.printStackTrace();
            try {
//                channel.basicNack(deliveryTag, false, true);
                channel.basicReject(deliveryTag, false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    /**
     *
     * @param string
     */
/*
    @RabbitListener(queues = {"soda.queue"})
    public void receive1( String string){
        System.out.println("收到消息: " + string);
        //message: class org.springframework.amqp.core.Message
        //收到消息：class org.springframework.amqp.core.Message
    }
*/

}
