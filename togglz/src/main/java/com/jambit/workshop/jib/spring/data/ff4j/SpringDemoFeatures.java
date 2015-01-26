package com.jambit.workshop.jib.spring.data.ff4j;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum SpringDemoFeatures implements Feature {

  @EnabledByDefault
  @Label("Admin Feature")
  ADMIN_FEATURE,

  @Label("HelloWorld Feature")
  HELLOWORLD_FEATURE,

  @Label("GoodbyeWorld Feature")
  GOODBYEWORLD_FEATURE,

  @InDev("Release 1.2.3")
  @Label("Jira-4711")
  FEATURE_JIRA_4711,

  @InDev("Release 1.2.3")
  @Label("Jira-4712")
  FEATURE_JIRA_4712;

  public boolean isActive() {
    return FeatureContext.getFeatureManager().isActive(this);
  }



}