package com.tssss.bysj.activity;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.login.LoginActivity;

public class StartActivity extends BaseActivity {
    private ImageButton mPlayIb, mRegisterIb;

    @Override
    protected void findViews() {
        mPlayIb = findViewById(R.id.start_play_ib);
        mRegisterIb = findViewById(R.id.start_register_ib);
    }

    @Override
    protected void setEventListeners() {
        mPlayIb.setOnClickListener(this);
        mRegisterIb.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void afterBindView() {

    }


    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_play_ib:
                openActivity(LoginActivity.class);
                break;
            case R.id.start_register_ib:
//                openActivity(RegisterActivity.class);
                break;
        }
    }
}
