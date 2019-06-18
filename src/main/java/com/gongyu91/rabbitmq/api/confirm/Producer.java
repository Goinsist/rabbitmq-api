package com.gongyu91.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.获取connection
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创建连接信道
        Channel channel = connection.createChannel();

        //4.指定消息投递模式
        channel.confirmSelect();
        String exchangeName="test_confirm_exchange";
        String routingKey="confirm.save";

        //5.发送一条消息
        String msg="Hello RabbitMQ send confirm message";

        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
        //6.添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("------ack!-----");
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.err.println("----no ack!----");
            }
        });
    }
}
