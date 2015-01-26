package com.jambit.workshop.jib.spring.data.helloworld.domain;

import java.time.MonthDay;

/**
 * Person as a pojo
 */
public class Customer {
    private String mFirstName;
    private String mLastName;
    private MonthDay mBirthday;
    private String mEmailAddress;

    public Customer() {}

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String mEmailAddress) {
        this.mEmailAddress = mEmailAddress;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public MonthDay getBirthday() {
        return mBirthday;
    }

    public void setBirthday(MonthDay mBirthday) {
        this.mBirthday = mBirthday;
    }



}
