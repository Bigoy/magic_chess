package com.tssss.bysj.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tssss.bysj.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 标准弹窗
 * <p>
 * 可以展现 标题、内容、操作按钮
 */
public class AlertDialog extends Dialog {
    private LinearLayout dialog_panel;
    private LinearLayout operation_bar;
    private TextView content;
    private TextView title;
    private TextView dialog_time;
    private DialogModel dialogModel;
    private View[] operationBar;
    private OnOperationBarClickListener onOperationBarClickListener;
    private Context context;
    /**
     * 操作按钮栏的索引位置
     */
    private int operationBarPosition;

    public AlertDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initDialog();
    }

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initDialog();
    }

    protected AlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.alert_dialog);
        findViews();
    }

    public void setDialogModel(DialogModel dialogModel) {
        if (null != dialogModel) {
            this.dialogModel = dialogModel;
            fillData();
        }
    }

    private void findViews() {
        this.title = findViewById(R.id.dialog_alter_title);
        this.dialog_time = findViewById(R.id.dialog_alter_time);
        this.content = findViewById(R.id.dialog_alter_content);
        this.dialog_panel = findViewById(R.id.dialog_panel);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getWindow().getDecorView().setBackgroundResource(R.drawable.bg_alter_dialog);
    }

    /**
     * 填充弹窗的数据到控件
     */
    private void fillData() {
        if (null != dialogModel) {
            title.setText(dialogModel.getTitle());
            dialog_time.setText(dialogModel.getTime());
            content.setText(dialogModel.getContent());
        }
    }

    /**
     * 给底部的操作按钮添加点击事件
     */
    private void initOperationBarClickListener() {
        if (null == this.operationBar || this.operationBar.length <= 0) {
            return;
        }
        OnOperationBarClickListener listener = this.onOperationBarClickListener;
        for (int i = 0; i < 3; i++) {
            operationBarPosition = i;
            if (null != operationBar[i]) {
                if (null != listener) {
                    operationBar[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.clickOperationBar(operationBarPosition);
                        }
                    });
                }
            }
        }
    }

    /**
     * 添加弹窗的操作按钮
     * <p>
     * 不能超过3个
     */
    private void addOperationViews() {
        if (null == this.operationBar) {
            return;
        }
        if (this.operationBar.length > 3) {
            throw new IllegalArgumentException("Dialog底部操作按钮最多只能有3个");
        }
        for (int i = 0; i < 3; i++) {
            operation_bar.addView(this.operationBar[i]);
        }
    }

    /**
     * 添加底部操作按钮
     */
    public void addOperationBar(View[] views) {
        this.operationBar = views;
        addOperationViews();
        initOperationBarClickListener();
    }

    /**
     * 设置OnOperationBarClickListener对象
     */
    public void setOnOperationBarClickListener(OnOperationBarClickListener listener) {
        if (null != listener) {
            this.onOperationBarClickListener = listener;
        }
    }

    /**
     * 显示弹窗
     */
    public void showDialog() {
        super.show();
        dialog_panel.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_slide_in));
    }

    /**
     * 取消弹窗
     */
    public void dismissDialog() {
        super.dismiss();

    }

    /**
     * 弹窗操作按钮的点击事件监听者接口
     */
    public interface OnOperationBarClickListener {
        /**
         * 事件处理方法
         *
         * @param index 操作控件的位置
         */
        void clickOperationBar(int index);
    }
}
