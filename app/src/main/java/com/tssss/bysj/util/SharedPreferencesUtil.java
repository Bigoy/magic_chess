package com.tssss.bysj.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public static void keepBoolean(Context context, String key, boolean b) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static boolean readBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
