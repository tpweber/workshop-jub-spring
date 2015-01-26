package com.jambit.workshop.jib.spring.data.ff4j;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.exception.FeatureNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Test for <code>HelloWorld</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class HelloWorldTest {

    @Autowired
    @Qualifier("ff4jBean")
    FF4j ff4jBean;

    @Autowired
    @Qualifier("BASE_HELLOWORLD")
    HelloWorld helloWorld;

    @Before
    public void setup() {
        assertNotNull(ff4jBean);
        ff4jBean.autoCreate(false);
        ff4jBean.disable("HELLOWORLD_FEATURE");
        ff4jBean.disable("GOODBYEWORLD_FEATURE");
        ff4jBean.disable("FEATURE_JIRA_4711");
        ff4jBean.disable("FEATURE_JIRA_4712");
        if(ff4jBean.exist(("NOT_EXISTING"))) {
            ff4jBean.delete("NOT_EXISTING");
        }
    }

    @Test
    public void testName() {
        assertTrue(true);
    }

    @Test
    public void testLocalFF4J() {
        FF4j ff4j = new FF4j("ff4j/ff4j.xml");
        testFF4J(ff4j);
    }

    @Test
    public void testFF4JBean() {
        testFF4J(ff4jBean);
    }

    private void testFF4J(final FF4j ff4j) {
        assertEquals(4, ff4j.getFeatures().size());
        testFeature(ff4j, "HELLOWORLD_FEATURE");
        testFeature(ff4j, "GOODBYEWORLD_FEATURE");
        testFeature(ff4j, "FEATURE_JIRA_4711");
        testFeature(ff4j, "FEATURE_JIRA_4712");
    }

    private void testFeature(final FF4j ff4j, final String featureUid) {
        assertTrue(ff4j.exist(featureUid));
        assertFalse(ff4j.check(featureUid));
        final Feature feature = ff4j.getFeature(featureUid);
        assertNotNull(feature);
        assertEquals(featureUid, feature.getUid());
        assertEquals(featureUid, feature.getDescription());
        assertEquals(featureUid, feature.getDescription());
        assertFalse(ff4j.check(featureUid));
        assertFalse(feature.isEnable());
        ff4j.enable(featureUid);
        assertTrue(ff4j.check(featureUid));
        assertTrue(feature.isEnable());
        ff4j.disable(featureUid);
        assertFalse(ff4j.check(featureUid));
        assertFalse(feature.isEnable());
    }

    @Test
    public void testAutoCreate() {
        FF4j ff4j = new FF4j("ff4j/ff4j.xml");
        testAutoCreate(ff4j);
        testAutoCreate(ff4jBean);
    }

    private void testAutoCreate(final FF4j ff4j) {
        boolean autocreateEnabled = ff4j.isAutocreate();
        ff4j.autoCreate(false);
        assertFalse(ff4j.exist("NOT_EXISTING"));
        try {
            ff4j.check("NOT_EXISTING");
            fail();
        } catch (FeatureNotFoundException fnfe) {
            assertTrue(fnfe.getMessage().startsWith("NOT_EXISTING"));
        }
        ff4j.autoCreate(true);
        assertFalse(ff4j.exist("NOT_EXISTING"));
        assertFalse(ff4j.check("NOT_EXISTING"));
        assertTrue(ff4j.exist("NOT_EXISTING"));
        Feature not_existing = ff4j.getFeature("NOT_EXISTING");
        assertNotNull(not_existing);
        assertFalse(not_existing.isEnable());
        ff4j.autoCreate(autocreateEnabled);
    }

    @Test
    public void testToggleClasses() {
        assertFalse(ff4jBean.check("FEATURE_JIRA_4711"));
        String sHello = helloWorld.sayHello("testToggle1");
        assertEquals("testToggle1", helloWorld.getName());
        assertNotNull(sHello);
        assertEquals("Hello testToggle1, version 1.0.0", sHello);

        ff4jBean.enable("FEATURE_JIRA_4711");
        assertTrue(ff4jBean.check("FEATURE_JIRA_4711"));
        String sHelloToggled = helloWorld.sayHello("testToggle2");
        //assertEquals("testToggle2", helloWorld.getName());
        assertNotNull(sHelloToggled);
        assertEquals("Welcome testToggle2, version 2.0.0", sHelloToggled);

        ff4jBean.disable("FEATURE_JIRA_4711");
        assertFalse(ff4jBean.check("FEATURE_JIRA_4711"));
        String sHelloToggledback = helloWorld.sayHello("testToggle3");
        assertEquals("testToggle3", helloWorld.getName());
        assertNotNull(sHelloToggledback);
        assertEquals("Hello testToggle3, version 1.0.0", sHelloToggledback);
    }

}
