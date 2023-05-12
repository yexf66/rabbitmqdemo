package org.example.simplequeue;

import com.rabbitmq.client.*;
import org.example.utils.RabbitMqConnectUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.example.simplequeue.Producer.QUEUE_NAME;

public class Consumer {

    @Test
    public void consume() throws Exception {
        Connection conn = RabbitMqConnectUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者获取到消息：" + new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, callback);
        System.out.println("开始监听队列");

        System.in.read();
    }

}
