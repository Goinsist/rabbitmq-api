package com.gongyu91.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1.创建一个ConnectionFactory
        ConnectionFactory connectionFactory=new ConnectionFactory();

        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //是否支持自动重连
        connectionFactory.setAutomaticRecoveryEnabled(true);
        //多少毫秒重连一次
        connectionFactory.setNetworkRecoveryInterval(3000);
        //2.通过连接工厂创建连接
        Connection connection= connectionFactory.newConnection();

        //3.通过connection创建一个Channel
        Channel channel=connection.createChannel();
        //4.声明
        String exchangeName="test_direct_exchange";
        String exchangeType="direct";
        String queueName="test_direct_queue";
        String routingKey="test.direct";
        //表示声明的一个交换机
       channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
       //表示声明的一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        //建立一个绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        //durable 是否持久化消息
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //参数:队列名称、是否自动化ACK、Consumer
        channel.basicConsume(queueName,true,consumer);



        //7.获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg= new String(delivery.getBody());
            System.out.println("消费端: "+msg);
            //Envelope envelope = delivery.getEnvelope();

        }
    }
}
