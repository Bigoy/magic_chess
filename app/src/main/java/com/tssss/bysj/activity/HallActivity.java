package com.tssss.bysj.activity;

import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;

public class HallActivity extends BaseActivity {
    @Override
    protected void findViews() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hall;
    }

    @Override
    protected int getLeftBtnStyle() {
        return R.drawable.ic_btn_quit;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.hall_title;
    }

    @Override
    public void onClick(View v) {
    }
}
