package com.codapayments.loadbalancerapi.exceptions;

/**
 * Exception thrown when no server is available to handle a request.
 */
public class NoServerAvailableException extends Exception {
    public NoServerAvailableException(String message) {
        super(message);
    }
}
