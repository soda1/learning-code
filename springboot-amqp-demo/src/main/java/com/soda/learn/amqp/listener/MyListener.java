package com.soda.learn.amqp.listener;


import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Configuration
public class MyListener {

    /**
     * 测试使用Object和Message是否为同一对象
     * @param object
     * @param message
     */
    //@RabbitListener(queues = {"soda.queue"})
    public void receive1(Object object, Message message){

        System.out.println(message == object);
        //收到消息：class org.springframework.amqp.core.Message
    }

    /**直接使用实际对象接收消息
     * @param string
     */
    //@RabbitListener(queues = {"soda.queue"})
    public void receive1( String string){
        System.out.println("收到消息: " + string);
        //message: class org.springframework.amqp.core.Message
        //收到消息：class org.springframework.amqp.core.Message
    }


    @RabbitListener(queues = {"soda.queue"})
    public void receive2(Message message, Channel channel) throws  Exception{
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            System.out.println("收到消息----" + message.getBody().toString());
            int i = 1/0;
            channel.basicAck(deliveryTag, false);


        } catch (Exception e) {

/*
            try {
                //拒绝确认，重发
                channel.basicNack(deliveryTag, false, true);

                //拒绝确认，不重发
//                channel.basicReject(deliveryTag, false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
*/

//            throw new Exception("消费消息失败，进行重发");

        }
    }


    /**
     * 死信消费队列
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {"soda.deadQueue"})
    public void deadQueueReceive(Message message, Channel channel) {

        System.out.println(message.getBody().toString());
        System.out.println("死信队列消费");    }




}
