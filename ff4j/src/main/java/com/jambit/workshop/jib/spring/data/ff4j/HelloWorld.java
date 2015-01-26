package com.jambit.workshop.jib.spring.data.ff4j;

import org.ff4j.aop.Flip;

import java.util.Optional;

/**
 * HelloWorld
 */
public interface HelloWorld {
    @Flip(name = "FEATURE_JIRA_4711", alterBean = "IMPROVED_HELLOWORLD")
    String sayHello(String name);

    String getName();

    @Flip(name = "FEATURE_JIRA_4712", alterClazz = ImprovedHelloWorldImpl.class)
    Optional<String> sayGoodbye(String name);
}
