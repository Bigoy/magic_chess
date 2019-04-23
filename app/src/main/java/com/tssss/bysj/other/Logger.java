package com.tssss.bysj.other;

import android.util.Log;

public class Logger {
    public static void log(String s) {
        Log.wtf("MagicChess", s);
    }

    public static void log(int s) {
        Log.wtf("MagicChess", String.valueOf(s));
    }

    public static void log(boolean s) {
        Log.wtf("MagicChess", String.valueOf(s));
    }

    public static void log(Class clazz, String s) {
        Log.wtf(clazz.getSimpleName(), s);
    }
}
