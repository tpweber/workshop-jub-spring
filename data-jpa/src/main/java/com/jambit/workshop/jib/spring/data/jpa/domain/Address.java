package com.jambit.workshop.jib.spring.data.jpa.domain;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Address.
 */
@Entity
public class Address extends AbstractEntity {

    @Column(name = "street", nullable = false)
    private String mStreet;
    @Column(name = "city", nullable = false)
    private String mCity;
    @Column(name = "country", nullable = false)
    private String mCountry;

    public Address(String street, String city, String country) {

        Assert.hasText(street, "Street must not be null or empty!");
        Assert.hasText(city, "City must not be null or empty!");
        Assert.hasText(country, "Country must not be null or empty!");

        mStreet = street;
        mCity = city;
        mCountry = country;
    }

    protected Address() {
        // nop
    }

    public Address getCopy() {
        return new Address(mStreet, mCity, mCountry);
    }

    public String getStreet() {
        return mStreet;
    }

    public String getCity() {
        return mCity;
    }

    public String getCountry() {
        return mCountry;
    }
}
