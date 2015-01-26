package com.jambit.workshop.jib.spring.data.ff4j;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The base implemetation of <code>HelloWorld</code>
 */
@Component("BASE_HELLOWORLD")
public class HelloWorldImpl implements HelloWorld {

  @Autowired
  private FF4j mFF4j;

  private String mName;

  @Override
  public String sayHello(final String name) {
    mName = name;
    return String.format("Hello %s, version 1.0.0", mName);
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public Optional<String> sayGoodbye(final String name) {
    mName = name;
    if(mFF4j.check("GOODBYEWORLD_FEATURE")) {
      return Optional.of(String.format("Goodbye %s, version 1.0.1", mName));
    }
    return Optional.empty();
  }

}