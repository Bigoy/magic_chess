package com.tssss.bysj.other;

import android.util.Log;

import java.util.Map;

public class Logger {
    public static void log(String s) {
        Log.wtf("MagicChess", s);
    }

    public static void log(Object o, String s) {
        Log.wtf(o.getClass().getSimpleName(), s);
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

    public static void log(Map<String, ?> map) {
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                Log.wtf(entry.getKey(), " = " + entry.getValue());
            }
        }

    }

    public static void log(Map<String, ?> map, String filterKey) {
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                if (!filterKey.equals(entry.getKey())) {
                    Log.wtf(entry.getKey(), " = " + entry.getValue());
                }
            }
        }

    }
}
