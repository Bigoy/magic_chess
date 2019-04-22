package com.tssss.bysj.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
public class SystemUtil {

    /**
     * Get screen size
     * size[0] = width, size[1] = height
     *
     * @param context context
     * @return result
     */
    @SuppressWarnings("deprecation")
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        int[] size = new int[2];
        size[0] = width;
        size[1] = height;

        return size;
    }

    /**
     * 获取系统当前时间
     * 格式为 xxxx-xx-xx-xx
     */
    public static String getCurrentTime(String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getCurrentTime() {
        return getCurrentTime("MM月dd日 HH:mm");
    }
}
