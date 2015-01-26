package com.jambit.workshop.jib.spring.data.helloworld;

import com.jambit.workshop.jib.spring.data.helloworld.config.HelloWorldConfig;
import com.jambit.workshop.jib.spring.data.helloworld.first.HelloWorld;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main for Helloworld with xml configuration
 */
public class HelloWorldMainWithXML {
    public static void main(String args[]) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring/helloworld-context.xml");
        HelloWorld bean = (HelloWorld) context.getBean("helloWorldBean");
        bean.setName("Spring 4 with XML Configuration!");
        bean.sayHello();
        context.close();
    }
}
