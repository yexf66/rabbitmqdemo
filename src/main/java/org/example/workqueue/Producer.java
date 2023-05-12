package org.example.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.utils.RabbitMqConnectUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * 工作队列
 * - 生产者：生产者和简单队列的形式是一样的，都是将消息推送到默认交换机。
 * - 消费者：让消费者关闭自动ack，并且设置消息的流控，最终实现消费者可以尽可能去多消费消息
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";

    @Test
    public void publish() throws Exception {
        Connection conn = RabbitMqConnectUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, ("hello rabbit--" + i).getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("消息发送成功");
        channel.close();

    }
}
