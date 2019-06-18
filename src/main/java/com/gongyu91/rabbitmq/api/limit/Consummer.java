package com.gongyu91.rabbitmq.api.limit;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//限流
public class Consummer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        String exchangeName="test_qos_exchange";
        String routingKey="qos.#";

        String queueName="test_qos_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);
        //1.限流方式 第一件事就是autoAck设置为false prefetcgCount 一次性只接受一条数据 如果不给ack应答 broker认为这条消息还没有被消费 不会发另外的消息
        channel.basicQos(0,1,false);
//autoAck 手工签收
        channel.basicConsume(queueName,false,new MyConsumer(channel));

    }
}
