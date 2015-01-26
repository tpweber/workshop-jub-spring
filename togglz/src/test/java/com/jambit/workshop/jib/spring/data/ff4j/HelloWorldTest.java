package com.jambit.workshop.jib.spring.data.ff4j;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.togglz.core.context.FeatureContext;
import org.togglz.junit.TogglzRule;

import java.util.Optional;

import static org.junit.Assert.*;
import static com.jambit.workshop.jib.spring.data.ff4j.SpringDemoFeatures.*;

/**
 * Test for <code>HelloWorld</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class HelloWorldTest {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allDisabled(SpringDemoFeatures.class);

    @Autowired
    @Qualifier("helloWorldService")
    HelloWorld helloWorld;

    @Autowired
    HelloWorldImpl helloWorldImpl;

    @Autowired
    ImprovedHelloWorldImpl improvedHelloWorld;

    @Before
    public void setup() {
        FeatureContext.clearCache();
        assertNotNull(helloWorld);
        assertNotNull(helloWorldImpl);
        assertNotNull(improvedHelloWorld);
        helloWorld.setName(null);
    }

    @Test
    public void testName() {
        assertNull(helloWorld.getName());
        helloWorld.setName("Test");
        assertEquals("Test", helloWorld.getName());
    }

    @Test
    public void testToggleFeature() {
        testToggle(FEATURE_JIRA_4711);
        testToggle(FEATURE_JIRA_4712);
        testToggle(GOODBYEWORLD_FEATURE);
        testToggle(HELLOWORLD_FEATURE);
    }

    private void testToggle(SpringDemoFeatures feature) {
        assertFalse(feature.isActive());
        togglzRule.enable(feature);
        assertTrue(feature.isActive());
        togglzRule.disable(feature);
        assertFalse(feature.isActive());
    }

    @Test
    public void testToggleClasses() {
        assertFalse(FEATURE_JIRA_4711.isActive());
        helloWorld.setName("testToggle");
        String sHello = helloWorld.sayHello();
        assertNotNull(sHello);
        assertEquals("Hello testToggle, version 1.0.0", sHello);

        togglzRule.enable(FEATURE_JIRA_4711);
        assertTrue(FEATURE_JIRA_4711.isActive());
        helloWorld.setName("testToggle");
        String sHelloToggled = helloWorld.sayHello();
        assertNotNull(sHelloToggled);
        assertEquals("Welcome testToggle, version 2.0.0", sHelloToggled);

        togglzRule.disable(FEATURE_JIRA_4711);
        assertFalse(FEATURE_JIRA_4711.isActive());
        helloWorld.setName("testToggle");
        String sHelloToggledback = helloWorld.sayHello();
        assertNotNull(sHelloToggledback);
        assertEquals("Hello testToggle, version 1.0.0", sHelloToggledback);
    }

    @Test
    public void testToggleMethodsHelloWorld() {
        assertFalse(FEATURE_JIRA_4711.isActive());
        assertFalse(GOODBYEWORLD_FEATURE.isActive());
        helloWorld.setName("testToggle");
        Optional<String> oBye = helloWorld.sayGoodbye();
        assertFalse(oBye.isPresent());

        togglzRule.enable(GOODBYEWORLD_FEATURE);
        assertTrue(GOODBYEWORLD_FEATURE.isActive());
        Optional<String> oByeToggled = helloWorld.sayGoodbye();
        assertTrue(oByeToggled.isPresent());
        assertEquals("Goodbye testToggle, version 1.0.1", oByeToggled.get());
    }

    @Test
    public void testToggleMethodsImprovedHelloWorld() {
        assertFalse(GOODBYEWORLD_FEATURE.isActive());
        togglzRule.enable(FEATURE_JIRA_4711);
        assertTrue(FEATURE_JIRA_4711.isActive());
        helloWorld.setName("testToggle");
        Optional<String> oBye = helloWorld.sayGoodbye();
        assertTrue(oBye.isPresent());
        assertEquals("Goodbye testToggle, version 2.0.0", oBye.get());

        togglzRule.enable(GOODBYEWORLD_FEATURE);
        assertTrue(GOODBYEWORLD_FEATURE.isActive());
        Optional<String> oByeToggled = helloWorld.sayGoodbye();
        assertTrue(oByeToggled.isPresent());
        assertEquals("Bye bye, you wonderful World, bye testToggle, version 2.1.0", oByeToggled.get());

        togglzRule.disable(GOODBYEWORLD_FEATURE);
        assertFalse(GOODBYEWORLD_FEATURE.isActive());
        helloWorld.setName("testToggle");
        Optional<String> oByeToggledBack = helloWorld.sayGoodbye();
        assertTrue(oByeToggledBack.isPresent());
        assertEquals("Goodbye testToggle, version 2.0.0", oByeToggledBack.get());
    }

    @Test
    public void testToggleHelloWorldFeature() {
        helloWorldImpl.setName("test");
        assertFalse(HELLOWORLD_FEATURE.isActive());
        String sayHelloActive = helloWorldImpl.sayHello();
        assertEquals("Hello test, version 1.0.0", sayHelloActive);
        togglzRule.enable(HELLOWORLD_FEATURE);
        assertTrue(HELLOWORLD_FEATURE.isActive());
        String sayHelloInactive = helloWorldImpl.sayHello();
        assertEquals("Hello test, version 1.0.0", sayHelloInactive);

        togglzRule.disable(HELLOWORLD_FEATURE);
        assertFalse(HELLOWORLD_FEATURE.isActive());
        improvedHelloWorld.setName("test");
        String sayHelloActive2 = improvedHelloWorld.sayHello();
        assertEquals("Welcome test, version 2.0.0", sayHelloActive2);
        togglzRule.enable(HELLOWORLD_FEATURE);
        assertTrue(HELLOWORLD_FEATURE.isActive());
        String sayHelloInactive2 = improvedHelloWorld.sayHello();
        assertEquals("Welcome test, version 2.1.0", sayHelloInactive2);
    }

}
