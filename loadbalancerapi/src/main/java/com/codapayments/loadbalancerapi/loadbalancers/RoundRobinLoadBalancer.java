package com.codapayments.loadbalancerapi.loadbalancers;

import org.slf4j.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codapayments.loadbalancerapi.ServerPool;
import com.codapayments.loadbalancerapi.contracts.LoadBalancer;
import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;
import com.codapayments.loadbalancerapi.models.Server;

@Service
public class RoundRobinLoadBalancer implements LoadBalancer {
    private final ServerPool serverPool;
    private static final Logger logger = LoggerFactory.getLogger(RoundRobinLoadBalancer.class);
    int currentServerIndex = 0;

    public RoundRobinLoadBalancer(ServerPool serverPool) {
        this.serverPool = serverPool;
    }

    private synchronized Server getNextServer() {
        try {
            int serverCount = serverPool.getServers().size();

            if (serverCount == 0) {
                return null;
            }

            Server server = serverPool.getServers().get(currentServerIndex);
            currentServerIndex = (currentServerIndex + 1) % serverCount;
            return server;
        } catch (Exception exception) {
            logger.error("Error while getting next server: " + exception.getMessage(), exception);
            return null;
        }
    }

    public ResponseEntity<?> forwardRequest(String path, HttpMethod method, String body)
            throws NoServerAvailableException {
        Server server = getNextServer();

        if (server == null) {
            throw new NoServerAvailableException("No servers available in server pool to handle the request");
        }

        return server.handleRequest(path, method, body);
    }
}
