package com.jambit.workshop.jib.spring.data.shared.exception;

/**
 * Exception for errors saving entities.
 */
public class EntityNotSavedException extends RuntimeException {
    private static final long serialVersionUID = 7L;

    public EntityNotSavedException(String message) {
        super(message);
    }

    public EntityNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
