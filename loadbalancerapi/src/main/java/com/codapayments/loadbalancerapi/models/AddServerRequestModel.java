package com.codapayments.loadbalancerapi.models;

public class AddServerRequestModel extends ServerBaseModel {
    // This class will be used to add a new server to the pool
    // It extends the ServerBaseModel to include the common properties of a server
    // Additional properties or methods can be added here if needed

    // No additional properties or methods are needed for now
    public AddServerRequestModel(String name, String ipAddress, int port, String baseUrl) {
        super(name, ipAddress, port, baseUrl);
    }
}
