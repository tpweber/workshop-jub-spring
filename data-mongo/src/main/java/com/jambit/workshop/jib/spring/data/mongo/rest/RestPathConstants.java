package com.jambit.workshop.jib.spring.data.mongo.rest;

/**
 * Constants for the different REST-paths.
 */
final class RestPathConstants {

  static final String VARIABLE_ID = "id";
  //
  static final String PATH_HELLO = "/hello";
  //
  static final String PATH_CUSTOMER = "/customer";
  static final String PATH_CUSTOMER_ID = PATH_CUSTOMER + "/{" + VARIABLE_ID + "}";
  //
  static final String PATH_PRODUCT = "/product";
  static final String PATH_PRODUCT_ID = PATH_PRODUCT + "/{" + VARIABLE_ID + "}";

  private RestPathConstants() {
    // nop
  }
}
