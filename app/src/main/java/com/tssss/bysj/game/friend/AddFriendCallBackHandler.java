package com.tssss.bysj.game.friend;

import android.content.Context;
import android.os.Handler;

import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.im.JMessageManager;
import com.tssss.bysj.util.ToastUtil;

public class AddFriendCallBackHandler implements JMessageManager.AddFriendCallBack {
    private AlertDialog.Builder builder;
    private Context context;
    private Handler handler;

    public AddFriendCallBackHandler(Context context) {
        this.context = context;
        this.handler = new Handler();

    }

    @Override
    public void requesting() {
        builder = new AlertDialog.Builder(context)
                .subDesc("正在发送请求...")
                .operationType(AlertDialog.OPERATION_TYPE_SIMPLE);
        builder.display();

    }

    @Override
    public void success() {
        builder.dismiss();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(context, "发送成功，慢慢等待TA的回复吧！", ToastUtil.TOAST_DEFAULT);
            }
        });

    }

    @Override
    public void notUser() {
        builder.dismiss();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(context, "用户不存在！", ToastUtil.TOAST_ERROR);
            }
        });

    }

    @Override
    public void fail(String errorMsg) {
        builder.dismiss();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(context, errorMsg, ToastUtil.TOAST_ERROR);
            }
        });

    }

    @Override
    public void isFriend() {
        builder.dismiss();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(context, "你们已经是好友关系了哟！", ToastUtil.TOAST_DEFAULT);
            }
        });

    }
}
