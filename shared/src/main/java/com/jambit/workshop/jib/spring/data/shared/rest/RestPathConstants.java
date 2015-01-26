package com.jambit.workshop.jib.spring.data.shared.rest;

/**
 * Constants for the different REST-paths.
 */
public final class RestPathConstants {

  public static final String VARIABLE_ID = "id";
  //
  public static final String PATH_HELLO = "/hello";
  //
  static final String PATH_CUSTOMER = "/customers";
  public static final String PATH_CUSTOMER_ID = PATH_CUSTOMER + "/{" + VARIABLE_ID + "}";
  //
  static final String PATH_PRODUCT = "/products";
  public static final String PATH_PRODUCT_ID = PATH_PRODUCT + "/{" + VARIABLE_ID + "}";
  public static final String PATH_PRODUCT_ALL = PATH_PRODUCT;
  public static final String PATH_PRODUCT_ADD = PATH_PRODUCT + "/add";
  public static final String PATH_PRODUCT_FIND = PATH_PRODUCT + "/find";

  private RestPathConstants() {
    // nop
  }
}
