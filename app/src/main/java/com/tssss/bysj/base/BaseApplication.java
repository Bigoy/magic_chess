package com.tssss.bysj.base;


import android.app.Application;
import android.os.Process;

public class BaseApplication extends Application {

    /**
     * 退出app进程
     */
    public static void exitApp() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
