package org.wildfly.examples.boundary.messaging.util;

public class AbortException extends Exception {

    public AbortException(String message) {
        super(message);
    }
}
