package org.example.simplequeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.utils.RabbitMqConnectUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * 简单队列
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";

    @Test
    public void publish() throws Exception {
        Connection conn = RabbitMqConnectUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicPublish("",QUEUE_NAME,null,"hello rabbit".getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送成功");
        channel.close();

    }
}
