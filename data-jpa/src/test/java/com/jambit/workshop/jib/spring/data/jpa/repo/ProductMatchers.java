package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.domain.Product;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

/**
 * Matchers for <code>Product</code>.
 */
public class ProductMatchers {

    public static <T> Matcher<T> with(Matcher<T> matcher) {
        return matcher;
    }

    public static Matcher<Product> named(String name) {
        return hasProperty("name", is(name));
    }
}
