package com.tssss.bysj.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Tssss
 */
public abstract class BaseDialog extends Dialog {
    protected Window window;


    public BaseDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    private void init() {
        setContentView(layout());
        findViews();
        instantiateObject();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        window = getWindow();
        if (null != window) {
            hideStatusBarAndNavigationBar();
            View decor = window.getDecorView();
            decor.setBackgroundColor(0x00ffffff);
        }
    }

    /**
     * 隐藏状态栏和导航栏，实现全屏
     */
    protected void hideStatusBarAndNavigationBar() {
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    @Override
    public void show() {
        setListeners();
        customDialog();
        super.show();
    }

    protected abstract void findViews();

    protected abstract void fillData();

    protected abstract int layout();

    protected abstract void setListeners();

    protected abstract void customDialog();

    protected abstract void instantiateObject();

}
