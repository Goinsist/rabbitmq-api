package com.gongyu91.rabbitmq.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        System.err.println("-----consume message-------");
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("envelope: " + envelope);
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));


    }
}