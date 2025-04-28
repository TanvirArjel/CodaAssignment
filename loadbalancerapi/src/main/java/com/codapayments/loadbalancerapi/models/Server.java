package com.codapayments.loadbalancerapi.models;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class Server extends ServerBaseModel {
    private final UUID id;

    private final WebClient webClient = WebClient.create();
    private final Logger logger = LoggerFactory.getLogger(Server.class);

    public Server(String name, String ipAddress, int port, String baseUri) {
        super(name, ipAddress, port, baseUri);
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    /**
     * Returns the base URL of the server.
     *
     * @return the base URL of the server
     */

    /**
     * Handles a request to the server using the specified HTTP method and path.
     * The request body is sent as a JSON string.
     *
     * @param path   the path of the request
     * @param method the HTTP method (GET, POST, PUT, DELETE)
     * @param body   the request body as a JSON string
     * @return the response from the server
     * @throws Exception if an error occurs while handling the request
     */
    public ResponseEntity<?> handleRequest(String path, HttpMethod method, String body) {
        try {
            logger.info("Server " + this.getName() + " handling request: " + "path=" + path + ", method=" + method
                    + ", body=" + body);

            ResponseEntity<?> response = webClient
                    .method(method)
                    .uri(getBaseUrl() + path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .toEntity(Object.class)
                    .block();

            return response;
        } catch (Exception exception) {
            logger.error("Error while handling request: " + exception.getMessage(), exception);
            return ResponseEntity.internalServerError().body("Error while processing request.");
        }
    }

    /**
     * Checks if the server is healthy by sending a GET request to the /health
     * endpoint.
     * Retries up to 3 times if the server is not healthy.
     *
     * @return true if the server is healthy, false otherwise
     * @return
     */
    public boolean isHealthy() {
        int retryCount = 0;
        int maxRetries = 3;
        try {
            while (retryCount < maxRetries) {
                ResponseEntity<String> response = webClient
                        .get()
                        .uri(this.getBaseUrl() + "/health")
                        .retrieve()
                        .toEntity(String.class)
                        .block();

                if (response != null && response.getStatusCode().is2xxSuccessful()) {
                    return true;
                } else {
                    retryCount++;
                    Thread.sleep(1000); // Wait for 1 second before retrying
                }
            }

            logger.error("Server " + this.getName() + " is unhealthy after " + maxRetries + " retries.");
            return false;
        } catch (Exception exception) {
            logger.error("Error while checking server health: " + exception.getMessage(), exception);
            return false;
        }
    }
}