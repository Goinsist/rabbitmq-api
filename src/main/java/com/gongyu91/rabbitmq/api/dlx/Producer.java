package com.gongyu91.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
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
        String exchange="test_dlx_exchange";
        String routingKey="dlx.save";


        String msg="Hello RabbitMQ DLX Message";

//        channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        for(int i=0;i<5;i++){
            AMQP.BasicProperties properties=new AMQP.BasicProperties.Builder().deliveryMode(2).contentEncoding("UTF-8").expiration("10000").build();
            channel.basicPublish(exchange,routingKey,true,properties,msg.getBytes());
        }


    }
}
