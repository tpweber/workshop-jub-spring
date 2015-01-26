package com.jambit.workshop.jib.spring.data.shared.exception;

/**
 *
 */
public class InvalidParameterException extends RuntimeException {
  private static final long serialVersionUID = 11L;

  public InvalidParameterException(String message) {
    super(message);
  }

  public InvalidParameterException(String message, Throwable cause) {
    super(message, cause);
  }
}
