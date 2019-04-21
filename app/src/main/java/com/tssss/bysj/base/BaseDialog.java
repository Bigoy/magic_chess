package com.tssss.bysj.base;

import android.app.Dialog;
import android.content.Context;
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
            View decor = window.getDecorView();
            decor.setBackgroundColor(0x00ffffff);
        }
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
