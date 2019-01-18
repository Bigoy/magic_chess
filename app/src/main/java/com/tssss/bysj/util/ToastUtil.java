package com.tssss.bysj.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tssss.bysj.R;
import com.tssss.bysj.widget.GTextView;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author Tssss
 * @date 2018-12-31
 */
public class ToastUtil {
    /**
     * Toast style
     */
    public static int TOAST_DEFAULT = 0;
    public static int TOAST_ERROR = 1;

    /**
     * @require text length should be less than 10,
     * the eleventh char will be replaced by "..." .
     */
    @SuppressLint("InflateParams")
    public static void showToast(Context context, String text, int style) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
        ConstraintLayout cl = view.findViewById(R.id.toast_container);
        if (style == TOAST_DEFAULT) {
            cl.setBackground(context.getDrawable(R.drawable.bg_toast));
        } else if (style == TOAST_ERROR) {
            cl.setBackground(context.getDrawable(R.drawable.bg_toast_error));
        }

        GTextView gtv = view.findViewById(R.id.toast_gtv);
        gtv.setTextColor(Color.WHITE);
        gtv.setText(filterText(text));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    private static String filterText(String text) {
        StringBuilder sb = new StringBuilder();
        if (text.length() > 14) {
            for (int i = 0; i < 14; i++) {
                sb.append(text.charAt(i));
            }
            sb.append("...");
            return sb.toString();
        }
        return text;
    }
}
