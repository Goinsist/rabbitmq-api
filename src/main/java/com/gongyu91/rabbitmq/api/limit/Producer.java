package com.gongyu91.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        String exchange="test_qos_exchange";
        String routingKey="qos.save";


        String msg="Hello RabbitMQ Qos Message";

//        channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        for(int i=0;i<5;i++){
            channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        }


    }
}
