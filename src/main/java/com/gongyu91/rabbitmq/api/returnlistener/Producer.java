package com.gongyu91.rabbitmq.api.returnlistener;

import com.rabbitmq.client.*;

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
        String exchange="test_return_exchange";
        String routingKey="return.save";
        String routingKeyError="abc.save";

        String msg="Hello RabbitMQ Return Message";
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode,
                                     String replyText,
                                     String exchange,
                                     String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                System.err.println("-------handle return------");
                System.out.println("replyCode: "+replyCode);
                System.out.println("replyText: "+replyText);
                System.out.println("exchange: "+exchange);
                System.out.println("routingKey: "+routingKey);
                System.out.println("properties: "+properties);
                System.out.println("body: "+new String(body));


            }
        });
//        channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
     channel.basicPublish(exchange,routingKeyError,true,null,msg.getBytes());

    }
}
