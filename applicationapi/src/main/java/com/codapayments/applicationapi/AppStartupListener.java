package com.codapayments.applicationapi;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.codapayments.applicationapi.models.Server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppStartupListener {
    private Logger logger = LoggerFactory.getLogger(AppStartupListener.class);
    private final WebServerApplicationContext webServerApplicationContext;
    private final WebClient webClient = WebClient.create();

    private static final String SERVER_POOL_URL = "http://localhost:8081/api/v1/servers";

    public AppStartupListener(WebServerApplicationContext webServerApplicationContext) {
        this.webServerApplicationContext = webServerApplicationContext;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void registerServerToServerPool() throws UnknownHostException, JsonProcessingException {
        try {
            // This method will be called when the application is fully started
            logger.info("Application is ready! and registering this application server to the server pool.");

            int serverPort = webServerApplicationContext.getWebServer().getPort();
            String serverIpAddress = InetAddress.getLocalHost().getHostAddress();
            String serverName = InetAddress.getLocalHost().getHostName();
            String serverBaseUrl = "http://localhost:" + serverPort;

            Server server = new Server(serverName, serverIpAddress, serverPort, serverBaseUrl);

            logger.info("Serer is running at port: " + serverPort);
            logger.info("Server is running at serverIpAddress: " + serverIpAddress);
            logger.info("Server is running at serverName: " + serverName);
            logger.info("Server is running at : " + serverBaseUrl);

            // Create ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Convert to JSON string
            String jsonString = mapper.writeValueAsString(server);

            HttpStatusCode httpStatusCode = webClient
                    .post()
                    .uri(SERVER_POOL_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonString)
                    .retrieve()
                    .toBodilessEntity()
                    .map(ResponseEntity::getStatusCode)
                    .block();

            if (httpStatusCode == HttpStatus.OK) {
                logger.info("Server registered successfully to the server pool.");
            } else {
                logger.error("Failed to register server to the server pool. Status code: " + httpStatusCode);
            }

        } catch (Exception exception) {
            logger.error("Error while registering server to the server pool: " + exception.getMessage(), exception);
        }
    }
}
