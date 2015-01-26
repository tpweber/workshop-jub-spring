package com.jambit.workshop.jib.spring.data.helloworld.first;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Test for <code>HelloWorld</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/helloworld-context.xml")
public class HelloWorldTest {

    @Autowired
    HelloWorld helloWorld;

    @Before
    public void setup() {
        assertNotNull(helloWorld);
    }

    @Test
    public void testName() {
        final String world = "World";
        assertNotNull(helloWorld.setName(world));
        final Optional<String> name = helloWorld.getName();
        assertNotNull(name);
        assertTrue(name.isPresent());
        assertEquals(world, name.get());
    }

    @Test
    public void testNameNull() {
        assertNotNull(helloWorld.setName(null));
        final Optional<String> name = helloWorld.getName();
        assertNotNull(name);
        assertFalse(name.isPresent());
    }
}
