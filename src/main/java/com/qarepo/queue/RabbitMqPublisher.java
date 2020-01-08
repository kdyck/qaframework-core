package com.qarepo.queue;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class RabbitMqPublisher {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMqPublisher.class);
    private StringWriter sw = new StringWriter();
    private String host;
    private int port;
    private String virtualHost;
    private int heartbeat;
    private String userName;
    private String password;

    public RabbitMqPublisher() {
        this.host = "localhost";
        this.port = 5672;
        this.virtualHost = "virtualHost";
        this.heartbeat = 60;
        this.userName = "guest";
        this.password = "guest";
    }

    public RabbitMqPublisher(String host, int port, String virtualHost, int heartbeat, String userName, String password) {
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

    Connection getConnection() {
        RabbitMqSettings settings = new RabbitMqSettings(getHost(), getPort(), getVirtualHost(),
                getHeartbeat(), getUserName(), getPassword());
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = null;
        try {
            connection = factory.newConnection();
            factory.setHost(settings.getHost());
            factory.setPort(settings.getPort());
            factory.setVirtualHost(settings.getVirtualHost());
            factory.setRequestedHeartbeat(settings.getHeartbeat());
            factory.setUsername(settings.getUserName());
            factory.setPassword(settings.getPassword());
            factory.useSslProtocol();
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
        return connection;
    }

    void publish(Connection connection, String exchangeName, String queueName, String msg) {
        LOGGER.info("Sending Message... ");

        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);

            channel.basicPublish(exchangeName, queueName, null, msg.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Message Sent");
            System.out.println(" [x] Sent '" + queueName + "' to RabbitMQ");
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
    }

}
