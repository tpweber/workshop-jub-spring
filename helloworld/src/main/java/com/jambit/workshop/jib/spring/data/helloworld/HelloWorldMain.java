package com.jambit.workshop.jib.spring.data.helloworld;

import com.jambit.workshop.jib.spring.data.helloworld.config.HelloWorldConfig;
import com.jambit.workshop.jib.spring.data.helloworld.first.HelloWorld;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Main for Helloworld
 */
public class HelloWorldMain {
    public static void main(String args[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfig.class);
        HelloWorld bean = (HelloWorld) context.getBean("helloWorldBean");
        bean.setName("Spring 4!");
        bean.sayHello();
        context.close();
    }
}
