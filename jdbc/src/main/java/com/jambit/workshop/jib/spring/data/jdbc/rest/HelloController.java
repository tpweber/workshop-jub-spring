package com.jambit.workshop.jib.spring.data.jdbc.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants;

/**
 * Simple REST for <code>Hello World!</code>
 */
@RestController("helloController")
public class HelloController {

  @RequestMapping(value= RestPathConstants.PATH_HELLO, method= RequestMethod.GET)
  public String sayHello() {
    return "Hello World!";
  }
}
