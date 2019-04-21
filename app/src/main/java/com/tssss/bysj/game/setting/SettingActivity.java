package com.tssss.bysj.game.setting;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.SharedPreferencesUtil;

import androidx.annotation.Nullable;

@ViewInject(layoutId = R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    private GTextView account_detail;
    private GTextView alter_password;
    private GTextView version;
    private GTextView version_description;
    private GTextView about;
    private LinearLayout voice_ll;
    private LinearLayout verbose_ll;
    private ImageView voice_iv;
    private ImageView verbose_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSettingTabState();
        initNotifyState();
    }

    private void initNotifyState() {
        if (SharedPreferencesUtil.readBoolean(this, Constant.SETTING_NOTIFY_VOICE)) {
            voice_iv.setImageResource(R.drawable.ic_switch_on);
        }
        if (!SharedPreferencesUtil.readBoolean(this, Constant.SETTING_NOTIFY_VOICE)) {
            voice_iv.setImageResource(R.drawable.ic_switch_off);
        }
        if (SharedPreferencesUtil.readBoolean(this, Constant.SETTING_NOTIFY_VERBOSE)) {
            verbose_iv.setImageResource(R.drawable.ic_switch_on);
        }
        if (!SharedPreferencesUtil.readBoolean(this, Constant.SETTING_NOTIFY_VERBOSE)) {
            verbose_iv.setImageResource(R.drawable.ic_switch_off);
        }
    }

    private void validateNotifyState() {
        initNotifyState();
    }

    @Override
    protected void findViews() {
        account_detail = findViewById(R.id.setting_account);
        alter_password = findViewById(R.id.setting_alter_psd);
        version = findViewById(R.id.setting_version);
        version_description = findViewById(R.id.setting_version_description);
        about = findViewById(R.id.setting_about_game);
        voice_ll = findViewById(R.id.setting_group_notify_voice);
        verbose_ll = findViewById(R.id.setting_group_notify_verbose);
        voice_iv = findViewById(R.id.setting_voice_iv);
        verbose_iv = findViewById(R.id.setting_verbose_iv);
    }

    @Override
    protected void setEventListeners() {
        account_detail.setOnClickListener(this);
        alter_password.setOnClickListener(this);
        version.setOnClickListener(this);
        version_description.setOnClickListener(this);
        about.setOnClickListener(this);
        voice_ll.setOnClickListener(this);
        verbose_ll.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        SharedPreferencesUtil.keepBoolean(this, Constant.SETTING_NOTIFY_VOICE, true);
        SharedPreferencesUtil.keepBoolean(this, Constant.SETTING_NOTIFY_VERBOSE, true);
        initSettingTabState();
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.setting_title;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setting_account:
                startColorAnimation(account_detail);
                openActivityDelay(AccountActivity.class, 110);
                break;
            case R.id.setting_alter_psd:
                startColorAnimation(alter_password);
                openActivityDelay(PasswordActivity.class, 110);
                break;
            case R.id.setting_version:
                startColorAnimation(version);
                break;
            case R.id.setting_version_description:
                startColorAnimation(version_description);
                break;
            case R.id.setting_about_game:
                startColorAnimation(about);
                break;
            case R.id.setting_group_notify_voice:
                switchNotifyState(voice_iv, Constant.SETTING_NOTIFY_VOICE);
                break;
            case R.id.setting_group_notify_verbose:
                switchNotifyState(verbose_iv, Constant.SETTING_NOTIFY_VERBOSE);
                break;
            default:

        }
    }

    private void switchNotifyState(ImageView v, String key) {
        initSettingTabState();
        v.setVisibility(View.INVISIBLE);
        if (SharedPreferencesUtil.readBoolean(this, key)) {
            v.setImageResource(R.drawable.ic_switch_off);
            SharedPreferencesUtil.keepBoolean(this, key, false);
        } else if (!SharedPreferencesUtil.readBoolean(this, key)) {
            v.setImageResource(R.drawable.ic_switch_on);
            SharedPreferencesUtil.keepBoolean(this, key, true);
        }
        AnimationUtil.flipView(this, v);
    }

    private void startColorAnimation(GTextView view) {
        initSettingTabState();
        view.setTextColor(Color.WHITE);
        ValueAnimator va = ObjectAnimator.ofInt(view, "backgroundColor",
                0x00000000, 0xFF7E561B);
        va.setDuration(100);
        va.setEvaluator(new ArgbEvaluator());
        va.start();
    }

    private void initSettingTabState() {
        account_detail.setTextColor(0xFF7E561B);
        alter_password.setTextColor(0xFF7E561B);
        version.setTextColor(0xFF7E561B);
        version_description.setTextColor(0xFF7E561B);
        about.setTextColor(0xFF7E561B);

        account_detail.setBackgroundColor(0x00000000);
        alter_password.setBackgroundColor(0x00000000);
        version.setBackgroundColor(0x00000000);
        version_description.setBackgroundColor(0x00000000);
        about.setBackgroundColor(0x00000000);
    }
}
