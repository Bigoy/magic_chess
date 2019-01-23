package com.tssss.bysj.application;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {
    private static ActivityManager activityManager;

    private List<Activity> mActivities;


    private ActivityManager() {
        mActivities = new ArrayList<>();
    }

    public static ActivityManager getActivityManager() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }

        return activityManager;
    }

    /**
     * Add an instance of Activity.
     */
    public void addActivityInstance(Activity activity) {
        if (mActivities != null) {
            mActivities.add(activity);

            if (mActivities.size() == 1) {
                Log.wtf(getClass().getSimpleName(), "1 activity");
            } else if (mActivities.size() > 1) {
                Log.wtf(getClass().getSimpleName(), mActivities.size() + " activities");
            }
        }
    }

    /**
     * Remove all activities in the task stack, be aware that this method cannot
     * be equated with exiting application progress.
     */
    public void removeActivities() {
        if (mActivities != null) {
            for (int i = mActivities.size() - 1; i >= 0; i--) {
                mActivities.get(i).finish();
                mActivities.remove(i);
            }
        }

        Log.wtf(getClass().getSimpleName(), mActivities.size() + " activities");
    }
}
