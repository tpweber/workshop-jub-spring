package com.jambit.workshop.jib.spring.data.ff4j;


import org.togglz.core.context.FeatureContext;
import org.togglz.core.user.FeatureUser;

import java.util.Optional;

/**
 * The improved and significantly better implemetation of <code>HelloWorld</code>
 */
public class ImprovedHelloWorldImpl implements HelloWorld {

  private String mName;

  @Override
  public String sayHello() {
    if(SpringDemoFeatures.HELLOWORLD_FEATURE.isActive()) {
      return String.format("Welcome %s, version 2.1.0", mName);
    }
    return String.format("Welcome %s, version 2.0.0", mName);
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
      final FeatureUser currentFeatureUser = FeatureContext.getFeatureManager().getCurrentFeatureUser();
      if(null != currentFeatureUser) {
        return Optional.of(String.format("Bye bye, you wonderful World, bye %s and dear %s, thanks for the fish. Version 2.1.0", mName, currentFeatureUser.getName()));
      }
      return Optional.of(String.format("Bye bye, you wonderful World, bye %s, version 2.1.0", mName));
    }
    return Optional.of(String.format("Goodbye %s, version 2.0.0", mName));
  }

}