package com.qarepo.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMqConsumer {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMqPublisher.class);
    private StringWriter sw = new StringWriter();
    private String host;
    private int port;
    private String virtualHost;
    private int heartbeat;
    private String userName;
    private String password;


    public RabbitMqConsumer() {
        this.host = "localhost";
        this.port = 5672;
        this.virtualHost = "virtualHost";
        this.heartbeat = 60;
        this.userName = "guest";
        this.password = "guest";
    }

    public RabbitMqConsumer(String host, int port, String virtualHost, int heartbeat, String userName, String password) {
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.heartbeat = heartbeat;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void consume(Connection connection, String queueName) {
        try {
            Channel channel = connection.createChannel();
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }
}
