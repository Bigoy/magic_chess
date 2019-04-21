package com.tssss.bysj.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tssss.bysj.user.User;

/**
 * 用户数据缓存
 *
 * 保存用户账户信息到本地
 */
public class UserDataCache {
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    /**
     * 保存用户信息
     */
    @SuppressLint("ApplySharedPref")
    public static void saveAccount(Context context, User user) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ACCOUNT, user.getUserId());
        editor.putString(PASSWORD, user.getUserPassword());
        editor.commit();
        if (editor.commit()) {
            Log.i("UserDataCache", "缓存用户信息成功");
            Log.i("UserDataCache", "account = " + readAccount(context, ACCOUNT));
            Log.i("UserDataCache", "password = " + readAccount(context, PASSWORD));
        }
    }

    /**
     * 读取用户信息
     */
    public static String readAccount(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
