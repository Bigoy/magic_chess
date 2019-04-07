package com.tssss.bysj.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tssss.bysj.R;

public class AnimationUtil {
    public static void flipView(Context context, View hide, View show) {
        if (hide.getVisibility() == View.VISIBLE) {
            Animation hideAnim = AnimationUtils.loadAnimation(context, R.anim.alpha_slide_out);
            Animation showAnim = AnimationUtils.loadAnimation(context, R.anim.alpha_slide_in);
            hide.clearAnimation();
            hide.startAnimation(hideAnim);
            hide.setVisibility(View.GONE);
            if (show.getVisibility() != View.VISIBLE) {
                show.setVisibility(View.VISIBLE);
            }
            show.startAnimation(showAnim);
        }
    }
}
