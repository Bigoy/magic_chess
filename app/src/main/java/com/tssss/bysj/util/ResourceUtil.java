package com.tssss.bysj.util;

import android.content.Context;

public class ResourceUtil {

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }
}
