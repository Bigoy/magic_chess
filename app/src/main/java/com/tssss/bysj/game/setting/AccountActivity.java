package com.tssss.bysj.game.setting;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.BaseApplication;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;

import cn.jpush.im.android.api.JMessageClient;

@ViewInject(layoutId = R.layout.activity_account)
public class AccountActivity extends BaseActivity {
    private GTextView security;
    private GTextView device;
    private ImageButton logout;

    private Handler handler;

    @Override
    protected void findViews() {
        security = findViewById(R.id.setting_security);
        device = findViewById(R.id.setting_account_device);
        logout = findViewById(R.id.account_log_out);
    }

    @Override
    protected void setEventListeners() {
        security.setOnClickListener(this);
        device.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSettingTabState();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setting_security:
                startColorAnimation(security);
                openActivityDelay(AccountStateActivity.class, 110);
                /*AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
                        .desc("账号状态")
                        .subDesc("安全")
                        .operationType(AlertDialog.OPERATION_TYPE_NO)
                        .noDesc("知道了")
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {

                            }

                            @Override
                            public void no() {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initSettingTabState();
                                    }
                                }, 120);
                            }
                        });
                builder1.display();*/
                break;
            case R.id.setting_account_device:
                startColorAnimation(device);
                openActivityDelay(DeviceActivity.class, 110);
                break;
            case R.id.account_log_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .desc("退出账户")
                        .subDesc("退出后，下次进入游戏需要重新登录")
                        .okDesc("退出当前账户")
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGOUT);
                                UserDataCache.clearUserCache();
                                JMessageClient.logout();
                                backLauncher();
                                BaseApplication.exitApp();
                            }

                            @Override
                            public void no() {

                            }
                        });
                builder.display();
                break;
            default:
        }
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.setting_account_title;
    }

    private void startColorAnimation(GTextView view) {
        initSettingTabState();
        view.setTextColor(Color.WHITE);
        AnimationUtil.startBackgroundColorAnimator(view);
    }

    private void initSettingTabState() {
        security.setTextColor(0xFF7E561B);
        device.setTextColor(0xFF7E561B);

        security.setBackgroundColor(0x00000000);
        device.setBackgroundColor(0x00000000);
    }
}
