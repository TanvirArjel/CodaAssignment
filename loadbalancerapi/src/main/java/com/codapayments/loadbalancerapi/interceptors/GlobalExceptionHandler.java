package com.codapayments.loadbalancerapi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception, HttpServletRequest request) {
        // Fetch URI
        String requestUri = request.getRequestURI();

        // Fetch Controller and Method Info if available
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

        String controllerName = "UnknownController";
        String methodName = "UnknownMethod";

        if (handler instanceof HandlerMethod handlerMethod) {
            controllerName = handlerMethod.getBeanType().getSimpleName(); // e.g., ServerPoolController
            methodName = handlerMethod.getMethod().getName(); // e.g., addServer
        }

        logger.error("Exception occurred in Controller: {}, Method: {}, URI: {}",
                controllerName, methodName, requestUri, exception);

        return ResponseEntity.internalServerError()
                .body("Something went wrong. Please try again later.");
    }
}
