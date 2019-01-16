package com.tssss.bysj.user;

import android.widget.SimpleCursorTreeAdapter;

public class User {

    /*
    userId: phone number
     */
    private long userId;
    private String userPassword;

    public User(long userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
