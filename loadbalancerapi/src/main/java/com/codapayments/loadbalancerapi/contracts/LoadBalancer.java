package com.codapayments.loadbalancerapi.contracts;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codapayments.loadbalancerapi.exceptions.NoServerAvailableException;

@Service
public interface LoadBalancer {
    /**
     * Forwards the request to the appropriate server based on the load balancing
     * strategy.
     *
     * @param path   The path of the request.
     * @param method The HTTP method of the request.
     * @param body   The body of the request.
     * @return The response from the server.
     * @throws NoServerAvailableException If no server is available to handle the
     *                                    request.
     * @throws Exception                  If an error occurs while forwarding the
     *                                    request.
     */
    ResponseEntity<?> forwardRequest(String path, HttpMethod method, String body)
            throws NoServerAvailableException;
}
