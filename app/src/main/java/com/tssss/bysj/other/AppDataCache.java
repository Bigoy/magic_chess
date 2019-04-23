package com.tssss.bysj.other;

import com.tssss.bysj.util.SharedPreferencesUtil;

public class AppDataCache {
    public static void keepAccountState(String state) {
        SharedPreferencesUtil.keepString(SharedPreferencesFactory.getAppSharedPreferences(), Constant.ACCOUNT_STATE, state);
    }

    public static String readAccountState() {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getAppSharedPreferences(), Constant.ACCOUNT_STATE);
    }
}
