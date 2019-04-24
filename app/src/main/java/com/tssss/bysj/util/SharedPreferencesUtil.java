package com.tssss.bysj.util;

import android.content.SharedPreferences;

import com.tssss.bysj.other.Logger;

public class SharedPreferencesUtil {
    public static void keepBoolean(SharedPreferences sp, String key, boolean b) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static boolean readBoolean(SharedPreferences sp, String key) {
        return sp.getBoolean(key, false);
    }

    public static void keepString(SharedPreferences sp, String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
        Logger.log(readString(sp, key));
    }

    public static String readString(SharedPreferences sp, String key) {
        return sp.getString(key, "");
    }

    public static void keepInt(SharedPreferences sp, String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int readInt(SharedPreferences sp, String key) {
        return sp.getInt(key, 0);
    }


}
