package com.jambit.workshop.jib.spring.data.mongo.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customers")
public class Customer extends AbstractDocument  {

  @TextIndexed
  @Field("firstName")
  private String mFirstName;
  @TextIndexed
  @Field("lastName")
  private String mLastName;
  @Indexed(unique=true)
  @Field("email")
  private String mEmailAddress;

  public Customer(@Value("#root.id") final String pId) {
    super(pId);
  }

  @PersistenceConstructor
  public Customer(@Value("#root.id") final String pId, @Value("#root.firstName") final String firstName,
                  @Value("#root.lastName") final String lastName, @Value("#root.email") final String email) {
    super(pId);
    updateName(firstName, lastName);
    updateEmail(email);
  }

  private void updateName(final String firstName, final String lastName) {
    mFirstName = firstName;
    mLastName = lastName;
  }

  private void updateEmail(final String email) {
    mEmailAddress = email;
  }

  public Customer withName(final String firstName, final String lastName) {
    updateName(firstName, lastName);
    return this;
  }

  public Customer withEmail(final String email) {
    updateEmail(email);
    return this;
  }

  public String getName() {
    return mFirstName + " " + mLastName;
  }

  public String getEmailAddress() {
    return mEmailAddress;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof Customer)) {
      return false;
    }
    final Customer that = (Customer) other;
    return new EqualsBuilder().appendSuper(super.equals(that)).
            append(mFirstName, that.mFirstName).append(mLastName, that.mLastName).
            append(mEmailAddress, that.mEmailAddress).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).
            append(mFirstName).append(mLastName).append(mEmailAddress).toHashCode();
  }
}
