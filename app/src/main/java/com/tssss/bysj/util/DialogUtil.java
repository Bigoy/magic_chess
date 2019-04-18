package com.tssss.bysj.util;

import android.content.Context;
import android.os.Looper;
import android.view.View;

import com.tssss.bysj.widget.AlertDialog;
import com.tssss.bysj.widget.DialogModel;

import androidx.annotation.NonNull;

/**
 * 封装标准dialog的使用逻辑
 */
public class DialogUtil {
    /**
     * 显示一个标准的弹窗，带有标题、时间戳、文本内容
     *
     * @param context      上下文
     * @param dialogModel  数据模型
     * @param listener     点击监听
     * @param operationBar 操作按钮
     */
    public static void showAlertDialog(@NonNull Context context, @NonNull DialogModel dialogModel,
                                       @NonNull AlertDialog.OnOperationBarClickListener listener,
                                       View[] operationBar) {
        AlertDialog alertDialog = new AlertDialog(context);
        if (null != listener) {
            alertDialog.setOnOperationBarClickListener(listener);
        }
        if (null != operationBar) {
            alertDialog.addOperationBar(operationBar);
        }
        if (null != dialogModel) {
            alertDialog.setDialogModel(dialogModel);
        }

        // 检查当前线程是否为UI线程
        if (!Thread.currentThread().getName().equalsIgnoreCase("main")) {
            Looper.prepare();
            alertDialog.showDialog();
            Looper.loop();
        }
        alertDialog.showDialog();
    }
}
