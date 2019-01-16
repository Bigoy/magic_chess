package com.tssss.bysj.user;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager userManager;

    private List<User> mUsers;

    private UserManager() {
        mUsers = new ArrayList<>();
    }

    public static UserManager getUserManager() {
        return userManager == null ? new UserManager() : userManager;
    }

    public void addUser(User user) {
        mUsers.add(user);
    }

    public User getUser() {
        return mUsers.size() > 0 ? mUsers.get(0) : null;
    }
}
