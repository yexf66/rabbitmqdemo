package org.example.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqConnectUtils {

    public static final String USERNAME = "guest";
    public static final String PASSWORD = "guest";
    public static final String VIRTUAL_HOST = "/";
    public static final String HOST = "47.106.233.16";
    public static final int PORT = 5672;

    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setHost(HOST);
        factory.setPort(PORT);
        return factory.newConnection();
    }

}
