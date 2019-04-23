package com.tssss.bysj.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesFactory {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void initSharedPreferencesFactory(Context c){
        context = c;
    }

    public static SharedPreferences getUserSharedPreferences() {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getAppSharedPreferences() {
        return context.getSharedPreferences("app", Context.MODE_PRIVATE);
    }
}
