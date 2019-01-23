package com.tssss.bysj.activity;

import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;

public class DistributeNewsActivity extends BaseActivity {
    @Override
    protected void findViews() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distribute_news;
    }

    @Override
    protected int getLeftBtnStyle() {
        return R.drawable.ic_btn_cancel;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.distribute_news_title;
    }

    @Override
    protected int getRightIbStyle() {
        return R.drawable.ic_btn_distribute;
    }

    /**
     * Distribute news.
     */
    @Override
    protected void clickRight() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
