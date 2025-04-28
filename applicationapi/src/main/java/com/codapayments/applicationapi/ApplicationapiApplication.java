package com.codapayments.applicationapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationapiApplication {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationapiApplication.class);

	public static void main(String[] args) {
		logger.info("Starting Application API...");
		SpringApplication.run(ApplicationapiApplication.class, args);
		logger.info("Application API started successfully.");
	}

}
