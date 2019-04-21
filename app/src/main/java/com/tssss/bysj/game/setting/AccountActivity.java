package com.tssss.bysj.game.setting;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.util.AnimationUtil;

@ViewInject(layoutId = R.layout.activity_account)
public class AccountActivity extends BaseActivity {
    private GTextView security;
    private GTextView alter_phone;

    @Override
    protected void findViews() {
        security = findViewById(R.id.setting_security);
        alter_phone = findViewById(R.id.setting_alter_phone);
    }

    @Override
    protected void setEventListeners() {
        security.setOnClickListener(this);
        alter_phone.setOnClickListener(this);
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
                startColorAnimation(alter_phone);
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
        alter_phone.setTextColor(0xFF7E561B);

        security.setBackgroundColor(0x00000000);
        alter_phone.setBackgroundColor(0x00000000);
    }
}
