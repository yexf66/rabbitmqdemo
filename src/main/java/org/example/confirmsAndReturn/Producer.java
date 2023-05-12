package org.example.confirmsAndReturn;

import com.rabbitmq.client.*;
import org.example.utils.RabbitMqConnectUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Producer端保证消息可靠投递，
 * confirm可以保证消息发送到交换机，return机制可以保证消息投递到队列
 * queueDeclare durable设为true，保证队列在rabbitmq重启后还存在，同时设置消息持久化(deliveryMode=2)
 */
public class Producer {
    public static final String QUEUE_NAME = "confirms";

    @Test
    public void publish() throws Exception {
        Connection conn = RabbitMqConnectUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //Confirm机制，可以通过Confirm效果保证消息一定送达到Exchange，官方提供了三种方式，选择了对于效率影响最低的异步回调的效果
        //开启Confirm机制
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息成功的发送到Exchange！");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息没有发送到Exchange，尝试重试，或者保存到数据库做其他补偿操作！");
            }
        });

        //设置Return回调，确认消息是否路由到了Queue
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息没有路由到指定队列，做其他的补偿措施！！");
            }
        });
//        channel.basicPublish("", QUEUE_NAME, null, "hello rabbit".getBytes(StandardCharsets.UTF_8));
        //在发送消息时，将basicPublish方法参数中的mandatory设置为true，即可开启Return机制，当消息没有路由到队列中时，就会执行return
//        channel.basicPublish("", QUEUE_NAME, true, null, "hello rabbit".getBytes(StandardCharsets.UTF_8));//正确示例
//        channel.basicPublish("", "asd", true, null, "hello rabbit".getBytes(StandardCharsets.UTF_8));//错误示例
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder().deliveryMode(2).build();
        channel.basicPublish("", QUEUE_NAME, true, props, "hello rabbit".getBytes(StandardCharsets.UTF_8));

        System.out.println("消息发送成功");
        System.in.read();

    }


}
