package com.tssss.bysj.componet.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseDialog;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.util.AnimationUtil;

/**
 * 标准弹窗
 * <p>
 * 可以展现 标题、内容、操作按钮
 */
public class AlertDialog extends BaseDialog {
    public static final String OPERATION_TYPE_OK = "OPERATION_TYPE_OK";
    public static final String OPERATION_TYPE_NO = "OPERATION_TYPE_NO";
    public static final String OPERATION_TYPE_NORMAL = "OPERATION_TYPE_NORMAL";
    public static final String OPERATION_TYPE_SIMPLE = "OPERATION_TYPE_SIMPLE";

    protected GTextView dialog_desc;
    protected GTextView dialog_sub_desc;
    protected GTextView dialog_ok;
    protected GTextView dialog_no;
    protected LinearLayout dialog_operation_ll;

    protected OnDialogOperationListener operationListener;
    private boolean supportAnimation = true;
    private Handler handler;
    private boolean isShowing;

    protected String dialogDesc;
    protected String dialogSubDesc;
    protected String okDesc = "确认";
    protected String noDesc = "取消";
    protected String operationType = OPERATION_TYPE_NORMAL;

    public AlertDialog(@NonNull Context context) {
        super(context);
    }

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void instantiateObject() {
        handler = new Handler();
    }

    @Override
    protected void findViews() {
        dialog_desc = findViewById(R.id.dialog_desc);
        dialog_sub_desc = findViewById(R.id.dialog_sub_desc);
        dialog_ok = findViewById(R.id.dialog_ok);
        dialog_no = findViewById(R.id.dialog_no);
        dialog_operation_ll = findViewById(R.id.dialog_operation_ll);
    }

    @Override
    protected void fillData() {
        dialog_desc.setText(dialogDesc);
        dialog_sub_desc.setText(dialogSubDesc);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_normal;
    }

    @Override
    protected void setListeners() {
        dialog_ok.setOnClickListener(v -> {
            if (supportAnimation) {
                AnimationUtil.startBackgroundColorAnimator(dialog_ok);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (null != operationListener) {
                        operationListener.ok();
                    }
                    dismiss();
                }
            }, 110);
        });
        dialog_no.setOnClickListener(v -> {
            if (supportAnimation) {
                AnimationUtil.startBackgroundColorAnimator(dialog_no);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (null != operationListener) {
                        operationListener.no();
                    }
                    dismiss();
                }
            }, 110);
        });
    }

    @Override
    protected void customDialog() {
        if (OPERATION_TYPE_OK.equals(operationType)) {
            findViewById(R.id.dialog_divider).setVisibility(View.VISIBLE);
            dialog_ok.setVisibility(View.VISIBLE);
        } else if (OPERATION_TYPE_NO.equals(operationType)) {
            findViewById(R.id.dialog_divider).setVisibility(View.VISIBLE);
            dialog_no.setVisibility(View.VISIBLE);
        } else if (OPERATION_TYPE_NORMAL.equals(operationType)) {
            findViewById(R.id.dialog_divider).setVisibility(View.VISIBLE);
            dialog_ok.setVisibility(View.VISIBLE);
            dialog_no.setVisibility(View.VISIBLE);
        } else if (OPERATION_TYPE_SIMPLE.equals(operationType)) {
            dialog_ok.setVisibility(View.GONE);
            dialog_no.setVisibility(View.GONE);
        }
    }

    public void desc(String desc) {
        dialogDesc = desc;
    }

    public void subDesc(String subDesc) {
        dialogSubDesc = subDesc;
    }

    public void okDesc(String okDesc) {
        if (this.operationType.equals(OPERATION_TYPE_NO)) {
            return;
        }
        this.okDesc = okDesc;
    }

    public void noDesc(String noDesc) {
        if (this.operationType.equals(OPERATION_TYPE_OK)) {
            return;
        }
        this.noDesc = noDesc;
    }

    public void supportAnimation(boolean supportAnimation) {
        this.supportAnimation = supportAnimation;

    }

    public void operationType(String type) {
        operationType = type;
    }

    public void operationListener(OnDialogOperationListener listener) {
        operationListener = listener;
    }

    public void display() {
        isShowing = true;
        dialog_ok.setText(okDesc);
        dialog_no.setText(noDesc);
        super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing) {
            isShowing = false;
            super.dismiss();
        }
    }

    public interface OnDialogOperationListener {
        void ok();

        void no();
    }

    public static class Builder {

        private AlertDialog alertDialog;

        public Builder(Context context) {
            alertDialog = new AlertDialog(context, false, null);
        }

        public Builder desc(String desc) {
            alertDialog.desc(desc);
            return this;
        }

        public Builder subDesc(String subDesc) {
            alertDialog.subDesc(subDesc);
            return this;
        }

        public Builder okDesc(String okDesc) {
            alertDialog.okDesc(okDesc);
            return this;
        }

        public Builder noDesc(String noDesc) {
            alertDialog.noDesc(noDesc);
            return this;
        }

        public Builder operationType(String type) {
            alertDialog.operationType(type);
            return this;
        }

        public Builder operationListener(OnDialogOperationListener listener) {
            alertDialog.operationListener(listener);
            return this;
        }

        public Builder supportAnimation(boolean supportAnimation) {
            alertDialog.supportAnimation(supportAnimation);
            return this;
        }

        public void display() {
            alertDialog.display();
        }

        public void dismiss() {
            alertDialog.dismiss();
        }
    }
}
