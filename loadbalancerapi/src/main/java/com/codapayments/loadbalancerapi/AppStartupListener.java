package com.codapayments.loadbalancerapi;

import org.slf4j.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupListener {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupListener.class);
    private final ServerPool serverPool;

    public AppStartupListener(ServerPool serverPool) {
        this.serverPool = serverPool;
        // This constructor will be called when the application starts
        System.out.println("Application has started successfully!");
    }

    // You can add more methods here to handle other startup events if needed
    // For example, you could implement ApplicationListener<ContextRefreshedEvent>
    // to handle context refresh events
    // or use @EventListener annotation for specific events.
    @EventListener(ApplicationReadyEvent.class)
    public void startCheckingServerPoolHealth() {
        logger.info("Starting server pool health check...");
        // Start a thread to check the server pool health
        new Thread(() -> {
            while (true) {
                try {
                    serverPool.checkServerPoolHealth();
                    Thread.sleep(5000); // Check every 5 seconds
                } catch (InterruptedException exception) {
                    logger.warn("Server pool health check interrupted", exception);
                    Thread.currentThread().interrupt(); // Restore interrupted status
                } catch (Exception exception) {
                    logger.error("Error while checking server pool health", exception);
                }
            }
        }).start();
    }
}
