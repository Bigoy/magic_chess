package com.tssss.bysj.base;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;

import com.tssss.bysj.other.SharedPreferencesFactory;
import com.tssss.bysj.util.SystemUtil;

import cn.jpush.im.android.api.JMessageClient;

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
        JMessageClient.init(this, true);
        SharedPreferencesFactory.initSharedPreferencesFactory(this);
        SystemUtil.init(this);
    }
}
