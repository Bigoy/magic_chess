package com.tssss.bysj.game.setting;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.SharedPreferencesFactory;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.SharedPreferencesUtil;

import androidx.annotation.Nullable;

@ViewInject(layoutId = R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    private GTextView accountDetail;
    private GTextView alterPassword;
    private GTextView version;
    private GTextView versionDescription;
    private GTextView about;
    private LinearLayout voiceLl;
    private LinearLayout verboseLl;
    private ImageView voiceIv;
    private ImageView verboseIv;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initSettingTabState();
    }

    private void initNotifyState() {
        if (SharedPreferencesUtil.readBoolean(SharedPreferencesFactory.getUserSharedPreferences(),Constant.SETTING_NOTIFY_VOICE)) {
            voiceIv.setImageResource(R.drawable.ic_switch_on);
        }
        if (!SharedPreferencesUtil.readBoolean(SharedPreferencesFactory.getUserSharedPreferences(),Constant.SETTING_NOTIFY_VOICE)) {
            voiceIv.setImageResource(R.drawable.ic_switch_off);
        }
        if (SharedPreferencesUtil.readBoolean(SharedPreferencesFactory.getUserSharedPreferences(),Constant.SETTING_NOTIFY_VERBOSE)) {
            verboseIv.setImageResource(R.drawable.ic_switch_on);
        }
        if (!SharedPreferencesUtil.readBoolean(SharedPreferencesFactory.getUserSharedPreferences(),Constant.SETTING_NOTIFY_VERBOSE)) {
            verboseIv.setImageResource(R.drawable.ic_switch_off);
        }
    }

    @Override
    protected void findViews() {
        accountDetail = findViewById(R.id.setting_account);
        alterPassword = findViewById(R.id.setting_alter_psd);
        version = findViewById(R.id.setting_version);
        versionDescription = findViewById(R.id.setting_version_description);
        about = findViewById(R.id.setting_about_game);
        voiceLl = findViewById(R.id.setting_group_notify_voice);
        verboseLl = findViewById(R.id.setting_group_notify_verbose);
        voiceIv = findViewById(R.id.setting_voice_iv);
        verboseIv = findViewById(R.id.setting_verbose_iv);
        scrollView = findViewById(R.id.setting_scroll);
    }

    @Override
    protected void setEventListeners() {
        accountDetail.setOnClickListener(this);
        alterPassword.setOnClickListener(this);
        version.setOnClickListener(this);
        versionDescription.setOnClickListener(this);
        about.setOnClickListener(this);
        voiceLl.setOnClickListener(this);
        verboseLl.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        initSettingTabState();
        initNotifyState();
    }

    @Override
    protected void clickTopBarCenter() {
        super.clickTopBarCenter();
        scrollView.fullScroll(View.FOCUS_UP);
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
                startColorAnimation(accountDetail);
                openActivityDelay(AccountActivity.class, 110);
                break;
            case R.id.setting_alter_psd:
                startColorAnimation(alterPassword);
                openActivityDelay(PasswordActivity.class, 110);
                break;
            case R.id.setting_version:
                startColorAnimation(version);
                break;
            case R.id.setting_version_description:
                startColorAnimation(versionDescription);
                break;
            case R.id.setting_about_game:
                startColorAnimation(about);
                break;
            case R.id.setting_group_notify_voice:
                switchNotifyState(voiceIv, Constant.SETTING_NOTIFY_VOICE);
                break;
            case R.id.setting_group_notify_verbose:
                switchNotifyState(verboseIv, Constant.SETTING_NOTIFY_VERBOSE);
                break;
            case R.id.setting_scroll:
                scrollView.smoothScrollTo(0, 0);
                break;
            default:

        }
    }

    private void switchNotifyState(ImageView v, String key) {
        initSettingTabState();
        v.setVisibility(View.INVISIBLE);
        if (SharedPreferencesUtil.readBoolean(SharedPreferencesFactory.getUserSharedPreferences(),key)) {
            v.setImageResource(R.drawable.ic_switch_off);
            SharedPreferencesUtil.keepBoolean(SharedPreferencesFactory.getUserSharedPreferences(), key, false);
        } else if (!SharedPreferencesUtil.readBoolean(SharedPreferencesFactory.getUserSharedPreferences(), key)) {
            v.setImageResource(R.drawable.ic_switch_on);
            SharedPreferencesUtil.keepBoolean(SharedPreferencesFactory.getUserSharedPreferences(), key, true);
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
        accountDetail.setTextColor(0xFF7E561B);
        alterPassword.setTextColor(0xFF7E561B);
        version.setTextColor(0xFF7E561B);
        versionDescription.setTextColor(0xFF7E561B);
        about.setTextColor(0xFF7E561B);

        accountDetail.setBackgroundColor(0x00000000);
        alterPassword.setBackgroundColor(0x00000000);
        version.setBackgroundColor(0x00000000);
        versionDescription.setBackgroundColor(0x00000000);
        about.setBackgroundColor(0x00000000);
    }
}
