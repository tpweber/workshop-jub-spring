package com.jambit.workshop.jib.spring.data.jpa.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

/**
 * Email address.
 */
@Embeddable
public class EmailAddress {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    public static final String ATTRIBUTE_EMAIL = "email";

    @Column(name = ATTRIBUTE_EMAIL, nullable = false, length = 255)
    private String value;


    public EmailAddress(final String emailAddress) {
        Assert.isTrue(isValid(emailAddress), "Invalid email address -> " + StringUtils.defaultString(emailAddress, "NULL"));
        this.value = emailAddress;
    }

    protected EmailAddress() {

    }


    public static boolean isValid(String candidate) {
        return candidate != null && PATTERN.matcher(candidate).matches();
    }


    @Override
    public String toString() {
        return value;
    }
}
