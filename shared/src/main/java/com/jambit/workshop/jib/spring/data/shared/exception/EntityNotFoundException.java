package com.jambit.workshop.jib.spring.data.shared.exception;

/**
 *
 */
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7L;

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
