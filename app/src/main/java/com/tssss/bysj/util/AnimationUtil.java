package com.tssss.bysj.util;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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

    public static void flipView(Context context, View v) {
        Animation hideAnim = AnimationUtils.loadAnimation(context, R.anim.alpha_slide_out);
        Animation showAnim = AnimationUtils.loadAnimation(context, R.anim.alpha_slide_in);
        if (v.getVisibility() == View.VISIBLE) {
            v.clearAnimation();
            v.startAnimation(hideAnim);
            v.startAnimation(showAnim);
        } else {
            v.setVisibility(View.VISIBLE);
            v.startAnimation(showAnim);
        }
    }

    public static void startBackgroundColorAnimator(TextView v) {
        v.setTextColor(Color.WHITE);
        ValueAnimator va = ObjectAnimator.ofInt(v, "backgroundColor",
                0x00000000, 0xFF7E561B);
        va.setDuration(100);
        va.setEvaluator(new ArgbEvaluator());
        va.start();
    }


    public static void startBackgroundColorAnimator(TextView v, int from,int to) {
        v.setTextColor(Color.WHITE);
        ValueAnimator va = ObjectAnimator.ofInt(v, "backgroundColor",
                from, to);
        va.setDuration(100);
        va.setEvaluator(new ArgbEvaluator());
        va.start();
    }

    public static void startBackgroundColorAnimator(View v, int from,int to) {
        ValueAnimator va = ObjectAnimator.ofInt(v, "backgroundColor",
                from, to);
        va.setDuration(100);
        va.setEvaluator(new ArgbEvaluator());
        va.start();
    }

    public static void startAlphaSlideIn(Context context, View v, Animation.AnimationListener listener) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_slide_in);
        if (null != listener) {
            animation.setAnimationListener(listener);
        }
        if (v.getVisibility() != View.VISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
        v.clearAnimation();
        v.startAnimation(animation);
    }

    public static void startAlphaSlideIn(Context context, View v) {
        startAlphaSlideIn(context, v, null);
    }

    public static void startButtonScale(Context context, View v, Animation.AnimationListener listener) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.button_scale);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        if (null != listener) {
            animation.setAnimationListener(listener);
        }
        if (v.getVisibility() != View.VISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
        v.clearAnimation();
        v.startAnimation(animation);
    }
}
