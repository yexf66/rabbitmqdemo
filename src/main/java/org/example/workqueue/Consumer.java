package org.example.workqueue;

import com.rabbitmq.client.*;
import org.example.utils.RabbitMqConnectUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.example.simplequeue.Producer.QUEUE_NAME;

public class Consumer {

    @Test
    public void consume1() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMqConnectUtils.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);

        //3.5 设置消息的流控
        channel.basicQos(1);

        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1号-获取到消息：" + new String(body, StandardCharsets.UTF_8));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Producer.QUEUE_NAME, false, callback);
        System.out.println("开始监听队列");

        System.in.read();
    }

    @Test
    public void consume2() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMqConnectUtils.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);

        //3.5 设置消息的流控
        channel.basicQos(1);

        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者2号-获取到消息：" + new String(body, StandardCharsets.UTF_8));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Producer.QUEUE_NAME, false, callback);
        System.out.println("开始监听队列");

        System.in.read();
    }

}
