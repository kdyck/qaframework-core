package com.qarepo.queue;

public class RabbitMqSettings {
    private String host;
    private int port;
    private String virtualHost;
    private int heartbeat;
    private String userName;
    private String password;

    public RabbitMqSettings(String host, int port, String virtualHost, int heartbeat, String userName, String password) {
        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.heartbeat = heartbeat;
        this.userName = userName;
        this.password = password;
    }

    /*public RabbitMqSettings() {
    }
*/
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
}
