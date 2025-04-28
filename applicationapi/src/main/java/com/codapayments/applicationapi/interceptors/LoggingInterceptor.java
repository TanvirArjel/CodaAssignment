package com.codapayments.applicationapi.interceptors;

import java.text.MessageFormat;

import org.slf4j.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "requestStartTime";

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws Exception {
        logger.info("Received request: [{} {}] on Thread: {}", request.getMethod(), request.getRequestURI(),
                Thread.currentThread().threadId());
        return true; // Continue with the request processing
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception exception)
            throws Exception {
        // Log the request processing time
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        long endTime = System.currentTimeMillis();
        long duration = (startTime != null) ? (endTime - startTime) : -1;

        logger.info("Request [{} {}] completed with status code: {} on Thread: {} in {} ms",
                request.getMethod(), request.getRequestURI(),
                response.getStatus(),
                Thread.currentThread().threadId(), duration);

        // Log unhandled exceptions
        if (exception != null) {
            String message = MessageFormat.format(
                    "Unhandled exception occurred during the request [{0} {1}] on Thread: {2}",
                    request.getMethod(), request.getRequestURI(),
                    Thread.currentThread().threadId());

            logger.error(message, exception);
        }
    }
}
