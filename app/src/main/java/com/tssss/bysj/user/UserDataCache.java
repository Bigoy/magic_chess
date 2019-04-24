package com.tssss.bysj.user;

import android.util.Log;

import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.other.SharedPreferencesFactory;
import com.tssss.bysj.util.SharedPreferencesUtil;

/**
 * 用户数据缓存
 * <p>
 * 保存用户账户信息到本地
 */
public class UserDataCache {

    /**
     * 保存用户信息
     */
    public static void saveAccount(User user) {
        if (null == user) {
            Log.w("UserDataCache", "user == null");
        } else {
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_ID, user.getUserId());
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_PASSWORD, user.getUserPassword());
            Logger.log(readAccount(Constant.ACCOUNT_ID));
            Logger.log(readAccount(Constant.ACCOUNT_PASSWORD));
        }

    }

    /**
     * 读取用户信息
     * id
     * 密码
     */
    public static String readAccount(String key) {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getUserSharedPreferences(), key);
    }

    /**
     * 用户退出登录时清除账户缓存信息
     */
    public static void clearUserCache() {

    }

    public static void keepLastLoginTime(String time) {
        SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_LOGIN_TIME, time);
    }

    public static String readLastLoginTime() {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_LOGIN_TIME);
    }
}
