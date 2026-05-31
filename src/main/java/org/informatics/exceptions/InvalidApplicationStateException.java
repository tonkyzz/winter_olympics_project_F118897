package org.informatics.exceptions;

public class InvalidApplicationStateException extends RuntimeException {
    public InvalidApplicationStateException(String message) {
        super(message);
    }
}
