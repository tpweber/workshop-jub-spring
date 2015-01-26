package com.jambit.workshop.jib.spring.data.jdbc.domain;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 */
public final class EmailAddress {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    private final String mEmailAddress;

    public EmailAddress(final String emailAddress) {
        mEmailAddress = emailAddress;
    }

    public String getAsString() {
        return mEmailAddress;
    }

    public boolean isValid() {
        return isValid(mEmailAddress);
    }

    public static boolean isValid(final String candidate) {
        return StringUtils.isNotBlank(candidate) && PATTERN.matcher(candidate).matches();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (null == other || getClass() != other.getClass()) {
            return false;
        }
        final EmailAddress that = (EmailAddress) other;
        return new EqualsBuilder().append(mEmailAddress, that.mEmailAddress).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mEmailAddress).toHashCode();
    }

    @Override
    public String toString() {
        return getAsString();
    }
}