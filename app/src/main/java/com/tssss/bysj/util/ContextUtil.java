package com.tssss.bysj.util;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.SoftReference;

public class ContextUtil {
    private static SoftReference<Context> applicationContext;

    private static SoftReference<Activity> currentActivity;

    public static void setApplicationContext(Context appContext) {
        applicationContext = new SoftReference<>(appContext);
    }

    public static void setCurrentActivity(Activity curActivity) {
        currentActivity = new SoftReference<>(curActivity);
    }
    public static Context getApplicationContext() {
        return applicationContext.get();
    }

    public static Activity getCurrentActivity() {
        return currentActivity.get();
    }
}
