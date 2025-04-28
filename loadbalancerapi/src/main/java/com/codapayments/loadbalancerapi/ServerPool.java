package com.codapayments.loadbalancerapi;

import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import com.codapayments.loadbalancerapi.models.Server;

@Service
public class ServerPool {
    private static final Logger logger = LoggerFactory.getLogger(ServerPool.class);
    private ArrayList<Server> servers;

    public ServerPool() {
        servers = new ArrayList<>();
    }

    public synchronized void addServer(Server server) {
        if (server == null) {
            throw new IllegalArgumentException("Server cannot be null");
        }

        servers.add(server);
    }

    public synchronized void removeServer(UUID serverId) {
        if (servers.isEmpty()) {
            throw new IllegalStateException("No servers available to remove");
        }

        if (serverId == null) {
            throw new IllegalArgumentException("Server ID cannot be null");
        }

        servers.removeIf(server -> server.getId().equals(serverId));
    }

    public int getServerCount() {
        return servers.size();
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    // This method can be used to check the health of the server pool
    public void checkServerPoolHealth() {
        if (getServerCount() == 0) {
            logger.error("No servers available in the server pool.");
            return;
        }

        for (Server server : servers) {
            if (!server.isHealthy()) {
                removeServer(server.getId()); // Remove unhealthy server
                logger.error("Server " + server.getName() + " is unhealthy and removed from the server pool.");
            } else {
                logger.info("Server " + server.getName() + " is healthy.");
            }
        }
    }
}
