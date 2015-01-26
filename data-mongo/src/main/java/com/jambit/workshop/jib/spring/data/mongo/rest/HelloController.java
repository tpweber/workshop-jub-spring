package com.jambit.workshop.jib.spring.data.mongo.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants;

/**
 * Simple REST for <code>Hello World for Spring Data MongoDB!</code>
 */
@RestController("helloController")
 public class HelloController {

  private static final DateTimeFormatter MDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  @RequestMapping(value= RestPathConstants.PATH_HELLO, method= RequestMethod.GET)
  public String sayHello() {
    return "Hello World for Spring Data MongoDB @" + LocalDateTime.now().format(MDateTimeFormatter);
  }
}
