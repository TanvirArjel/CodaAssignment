package com.codapayments.applicationapi.models;

public class Server {
    private String name;
    private String ipAddress;
    private int port;
    private String baseUrl;

    public Server(String name, String ipAddress, int port, String baseUrl) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
        this.baseUrl = baseUrl;

    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
