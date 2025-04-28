package com.codapayments.loadbalancerapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadbalancerapiApplication {
	private static final Logger logger = LoggerFactory.getLogger(LoadbalancerapiApplication.class);

	public static void main(String[] args) {
		logger.info("Starting LoadbalancerapiApplication...");
		SpringApplication.run(LoadbalancerapiApplication.class, args);
		logger.info("LoadbalancerapiApplication started successfully.");
	}
}
