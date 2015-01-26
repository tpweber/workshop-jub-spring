package com.jambit.workshop.jib.spring.data.ff4j;

import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The improved and significantly better implemetation of <code>HelloWorld</code>
 */
@Component("IMPROVED_HELLOWORLD")
public class ImprovedHelloWorldImpl implements HelloWorld {

  @Autowired
  private FF4j mFF4j;

  private String mName;

  @Override
  public String sayHello(final String name) {
    mName = name;
    if(mFF4j.check("HELLOWORLD_FEATURE")) {
      return String.format("Welcome %s, version 2.1.0", mName);
    }
    return String.format("Welcome %s, version 2.0.0", mName);
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public Optional<String> sayGoodbye(final String name) {
    mName = name;
    if(mFF4j.check("GOODBYEWORLD_FEATURE")) {
      return Optional.of(String.format("Bye bye, you wonderful World, bye %s, version 2.1.0", mName));
    }
    return Optional.of(String.format("Goodbye %s, version 2.0.0", mName));
  }

}