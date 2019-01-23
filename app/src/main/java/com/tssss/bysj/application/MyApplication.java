package com.tssss.bysj.application;

public class MyApplication {
    private static MyApplication myApplication;

    private ActivityManager mActivityManager;


    private MyApplication() {
        mActivityManager = ActivityManager.getActivityManager();
    }

    public static MyApplication getMyApplication() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }

        return myApplication;
    }

    public void killAppProgress() {
        // Remove all activities.
        mActivityManager.removeActivities();
        // Close all servers.
        // Exit application.
        System.exit(0);
    }
}
