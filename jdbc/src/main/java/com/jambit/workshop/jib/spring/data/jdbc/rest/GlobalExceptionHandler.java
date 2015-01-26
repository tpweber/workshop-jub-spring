package com.jambit.workshop.jib.spring.data.jdbc.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;

/**
 * Handler for exceptions, mapping to http status codes.
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

  @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid Parameter")  // 400
  @ExceptionHandler(InvalidParameterException.class)
  public void handleBadRequest(){
    // nop
  }

  @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Entity not found")  // 404
  @ExceptionHandler(EntityNotFoundException.class)
  public void handleNotFound(){
    // nop
  }

  @ResponseStatus(value= HttpStatus.I_AM_A_TEAPOT, reason="Oops...")  // 418
  @ExceptionHandler(Exception.class)
  public void handleTeapot(){
    // nop
  }
}
