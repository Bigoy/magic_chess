package com.tssss.bysj.user;

public class User {

    /*
    userId: phone number
     */
    private long userId;
    private String userPassword;


    public User() {
    }

    public User(long userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
