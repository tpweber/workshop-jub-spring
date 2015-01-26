package com.jambit.workshop.jib.spring.data.helloworld.first;

import java.util.Optional;

/**
 * Hello world ;-)
 */
public class HelloWorldImpl implements HelloWorld{

    private String mName;

    public void sayHello() {
        final Optional<String> oName = getName();
        final String greeting = oName.isPresent() ? oName.get() : "world!";
        System.out.println("Hello " + greeting);
    }

    @Override
    public HelloWorld setName(String name) {
        mName = name;
        return this;
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(mName);
    }


}
