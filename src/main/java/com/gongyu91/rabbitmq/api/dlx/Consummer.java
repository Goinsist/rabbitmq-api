package com.gongyu91.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consummer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        //这就是一个普通的交换机和队列以及路由
        String exchangeName="test_dlx_exchange";
        String routingKey="dlx.#";

        String queueName="test_dlx_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        Map<String,Object> arguments=new HashMap<>();
        arguments.put("x-dead-letter-exchange","dlx.exchange");


        //argument属性,要设置到声明队列上
        channel.queueDeclare(queueName,true,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routingKey);
        //要进行死信队列的声明
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");

        channel.basicConsume(queueName,true,new MyConsumer(channel));

    }
}
