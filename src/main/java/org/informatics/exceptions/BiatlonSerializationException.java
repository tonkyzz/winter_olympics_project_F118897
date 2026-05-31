package org.informatics.exceptions;

public class BiatlonSerializationException extends RuntimeException {

    public BiatlonSerializationException(String message) {
        super(message);
    }

    public BiatlonSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
