package com.gongyu91.rabbitmq.api.returnlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consummer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        String exchangeName="test_return_exchange";
        String routingKey="return.#";

        String queueName="test_return_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);


        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,queueingConsumer);
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
           String msg= new String(delivery.getBody());
            System.out.println("消费端接收: "+msg);
        }
    }
}
