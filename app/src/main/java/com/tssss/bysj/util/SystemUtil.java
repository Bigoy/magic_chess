package com.tssss.bysj.util;

import android.content.Context;
import android.view.WindowManager;

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
}
