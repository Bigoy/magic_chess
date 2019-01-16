package com.tssss.bysj.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.interfaces.OnGDialogListener;

import androidx.annotation.NonNull;

/*
简易的自定义弹窗。
使用步骤：
GDialog gDialog = new GDialog(context, mContent);
gDialog.setOnGDialogListener(listener);
gDialog.showToast();
 */
public class GDialog extends Dialog {
    private Context context;
    private OnGDialogListener onGDialogListener;

    private GTextView mTitleGtv, mContentGtv;
    private ImageButton mPassiveIb, mPositiveIb;

    private String mContent, mTitle;

    public GDialog(Context context, String mTitle, String mContent) {
        super(context);
        this.context = context;
        this.mContent = mContent;
        this.mTitle = mTitle;
        init();
    }

    private void init() {
        // 1 加载dialog的视图布局。
        setContentView(R.layout.dialog);
        // 2 实例化提示内容、取消按钮、确认按钮对象。
        mTitleGtv = findViewById(R.id.g_dialog_title_gtv);
        mContentGtv = findViewById(R.id.g_dialog_content_gtv);
        mPassiveIb = findViewById(R.id.g_dialog_passive_ib);
        mPositiveIb = findViewById(R.id.g_dialog_positive_ib);

        // 3  按钮事件监听。
        mPassiveIb.setOnClickListener(new ClickListener());
        mPositiveIb.setOnClickListener(new ClickListener());
        // 4 设置提示文本。
        mTitleGtv.setText(mTitle);
        mContentGtv.setText(mContent);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.bg_dialog));
        }
    }

    /*
    按钮点击时间监听器。
     */
    public void setOnGDialogListener(OnGDialogListener onGDialogListener) {
        this.onGDialogListener = onGDialogListener;
    }

    /*
    显示弹窗。
     */
    @Override
    public void show() {
        super.show();
    }

    /*
    取消弹窗。
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.g_dialog_passive_ib:
                    onGDialogListener.onPassive();
                    dismiss();
                    break;
                case R.id.g_dialog_positive_ib:
                    onGDialogListener.onPositive();
                    dismiss();
                    break;
            }
        }
    }
}

