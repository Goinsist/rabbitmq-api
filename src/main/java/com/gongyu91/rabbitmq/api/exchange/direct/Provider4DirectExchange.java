package com.gongyu91.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider4DirectExchange {
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

        //4.声明
        String exchangeName="test_direct_exchange";
        String routingKey="test.direct";

        //5.发送

        String msg="Hello RabbitMQ 4 Direct Exchange Message...!";

            //参数:1.交换机 2.routtingkey
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());


        //5.记得要关闭相关的连接
//        channel.close();
//        connection.close();

    }
}
