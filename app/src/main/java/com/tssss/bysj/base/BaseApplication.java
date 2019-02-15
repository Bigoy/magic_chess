package com.tssss.bysj.base;


import android.os.Process;

public class BaseApplication {

    public static void exitApp() {
        Process.killProcess(Process.myPid());
    }
}
