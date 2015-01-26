package com.jambit.workshop.jib.spring.data.helloworld.first;

import java.util.Optional;

/**
 * Hello world ;-)
 */
public interface HelloWorld {
    void sayHello();

    HelloWorld setName(String name);

    Optional<String> getName();
}
