package com.jambit.workshop.jib.spring.data.helloworld.config;

import com.jambit.workshop.jib.spring.data.helloworld.first.HelloWorld;
import com.jambit.workshop.jib.spring.data.helloworld.first.HelloWorldImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Java-based configuration for HelloWorld
 */
@Configuration
public class HelloWorldConfig {
    @Bean(name="helloWorldBean")
    @Description("This is a just another HelloWorld Bean")
    public HelloWorld helloWorld() {
        return new HelloWorldImpl();
    }
}
