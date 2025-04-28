package com.codapayments.loadbalancerapi.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public abstract class ServerBaseModel {
    @NotNull(message = "Server name cannot be null")
    @NotEmpty(message = "Server name cannot be empty")
    @Size(min = 3, max = 100, message = "Server name must be between 3 and 100 characters")
    protected String name;

    @NotNull(message = "IP address cannot be null")
    @NotEmpty(message = "IP address cannot be empty")
    @Size(min = 7, max = 15, message = "IP address must be between 7 and 15 characters")
    protected String ipAddress;

    @Min(value = 1000, message = "Port must be greater than 1000")
    @Max(value = 9999, message = "Port must be less than or equal to 9999")
    protected int port;

    @NotNull(message = "Base URL cannot be null")
    @NotEmpty(message = "Base URL cannot be empty")
    @Size(min = 5, max = 200, message = "Base URL must be between 5 and 200 characters")
    protected String baseUrl;

    public ServerBaseModel(String name, String ipAddress, int port, String baseUrl) {
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
