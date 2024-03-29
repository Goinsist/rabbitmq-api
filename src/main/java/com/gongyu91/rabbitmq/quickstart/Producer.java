package com.gongyu91.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个ConnectionFactory
        ConnectionFactory connectionFactory=new ConnectionFactory();

        connectionFactory.setHost("106.14.113.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2.通过连接工厂创建连接
        Connection connection= connectionFactory.newConnection();

        //3.通过connection创建一个Channel
        Channel channel=connection.createChannel();

        //4.通过Channel发送数据
        String msg="Hello RabbitMQ!";
        for (int i=0;i<5;i++){
        //参数:1.交换机 2.routtingkey
            channel.basicPublish("","test001",null,msg.getBytes());
        }

        //5.记得要关闭相关的连接
        channel.close();
        connection.close();

    }
}
