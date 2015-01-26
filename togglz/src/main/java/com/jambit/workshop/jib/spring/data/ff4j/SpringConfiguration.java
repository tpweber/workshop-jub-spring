package com.jambit.workshop.jib.spring.data.ff4j;

import org.springframework.stereotype.Component;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

@Component
public class SpringConfiguration implements TogglzConfig {

  public Class<? extends Feature> getFeatureClass() {
    return SpringDemoFeatures.class;
  }

  public StateRepository getStateRepository() {
    return new InMemoryStateRepository();
  }

  public UserProvider getUserProvider() {
    return () -> new SimpleFeatureUser("thorsten", true);
  }
}