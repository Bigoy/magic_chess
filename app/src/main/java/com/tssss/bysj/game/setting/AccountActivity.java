package com.tssss.bysj.game.setting;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.BaseApplication;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;

import cn.jpush.im.android.api.JMessageClient;

@ViewInject(layoutId = R.layout.activity_account)
public class AccountActivity extends BaseActivity {
    private GTextView security;
    private GTextView alterPhone;
    private ImageButton logout;

    @Override
    protected void findViews() {
        security = findViewById(R.id.setting_security);
        alterPhone = findViewById(R.id.setting_alter_phone);
    }

    @Override
    protected void setEventListeners() {
        security.setOnClickListener(this);
        alterPhone.setOnClickListener(this);
        logout.findViewById(R.id.account_log_out);
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setting_security:
                startColorAnimation(security);
                break;
            case R.id.setting_alter_phone:
                startColorAnimation(alterPhone);
                break;
            case R.id.account_log_out:
                AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGOUT);
                JMessageClient.logout();
                BaseApplication.exitApp();
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
        alterPhone.setTextColor(0xFF7E561B);

        security.setBackgroundColor(0x00000000);
        alterPhone.setBackgroundColor(0x00000000);
    }
}
