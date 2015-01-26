package com.jambit.workshop.jib.spring.data.ff4j;

import org.togglz.core.annotation.FeatureGroup;
import org.togglz.core.annotation.Label;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@FeatureGroup
@Label("In Developement")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InDev {
    String value() default "";
}
