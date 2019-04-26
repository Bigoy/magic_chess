package com.tssss.bysj.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
public class SystemUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void init(Context c) {
        context = c;
    }

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

    public static String chargeSecondsToNowTime(String seconds) {

        long time = Long.parseLong(seconds)*1000-8*3600*1000;
        SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日 HH:mm");
        return format2.format(new Date(time));

    }

    public static String getCurrentTime() {
        return getCurrentTime("MM月dd日 HH:mm");
    }


    /**
     * 检查网络是否可用
     */
    public static boolean checkNet() {
        if (context == null){
            return false;
        }
        ConnectivityManager con = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isAvailable();
    }
}
