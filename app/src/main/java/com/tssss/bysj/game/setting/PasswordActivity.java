package com.tssss.bysj.game.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.main.MainActivity;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

@ViewInject(layoutId = R.layout.activity_password)
public class PasswordActivity extends BaseActivity {
    private EditText oldPsd;
    private EditText newPsd;
    private ImageButton alter;


    @Override
    protected void findViews() {
        oldPsd = findViewById(R.id.password_old);
        newPsd = findViewById(R.id.password_new);
        alter = findViewById(R.id.alter_ib);

    }

    @Override
    protected void setEventListeners() {
        alter.setOnClickListener(this);

    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.setting_password;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.alter_ib) {
            alterPassword();
        }
    }

    private void alterPassword() {
        String oldPsdStr = oldPsd.getText().toString();
        String newPsdStr = newPsd.getText().toString();

        if (StringUtil.isBlank(oldPsdStr) || StringUtil.isBlank(newPsdStr)) {
            ToastUtil.showToast(this, "输入内容不能为空", ToastUtil.TOAST_ERROR);

        } else {
            if (AccountUtil.validPassword(newPsdStr)) {
                if (!oldPsdStr.equals(newPsdStr)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .operationType(AlertDialog.OPERATION_TYPE_SIMPLE)
                            .desc("请等待...");
                    builder.display();

                    JMessageClient.updateUserPassword(oldPsdStr, newPsdStr, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                builder.dismiss();
                                UserDataCache.clearUserCache();
                                JMessageClient.logout();
                                AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGOUT);
                                openActivity(MainActivity.class);
                                ToastUtil.showToast(PasswordActivity.this, "更新成功，请重新登录", ToastUtil.TOAST_DEFAULT);
                                Logger.log("旧密码" + oldPsdStr);
                                Logger.log("新密码o" + newPsdStr);

                            } else {
                                builder.dismiss();
                                ToastUtil.showToast(PasswordActivity.this, "更新失败" + s, ToastUtil.TOAST_ERROR);
                                Logger.log(i + s);

                            }
                        }
                    });

                } else {
                    ToastUtil.showToast(this, "新密码不能和旧密码一样", ToastUtil.TOAST_ERROR);
                }

            } else {
                ToastUtil.showToast(this, "新密码格式不正确", ToastUtil.TOAST_ERROR);

            }
        }
    }
}
