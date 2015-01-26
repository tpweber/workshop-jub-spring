package com.jambit.workshop.jib.spring.data.ff4j;


import java.util.Optional;

/**
 * The base implemetation of <code>HelloWorld</code>
 */
public class HelloWorldImpl implements HelloWorld {

  private String mName;

  @Override
  public String sayHello() {
    return String.format("Hello %s, version 1.0.0", mName);
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setName(String name) {
    mName = name;
  }

  @Override
  public Optional<String> sayGoodbye() {
    if(SpringDemoFeatures.GOODBYEWORLD_FEATURE.isActive()) {
      return Optional.of(String.format("Goodbye %s, version 1.0.1", mName));
    }
    return Optional.empty();
  }

}