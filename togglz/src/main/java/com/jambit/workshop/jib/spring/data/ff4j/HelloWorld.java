package com.jambit.workshop.jib.spring.data.ff4j;

import java.util.Optional;

/**
 * HelloWorld
 */
public interface HelloWorld {
    String sayHello();

    String getName();

    void setName(String name);

    Optional<String> sayGoodbye();
}
