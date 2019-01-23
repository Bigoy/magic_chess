package com.tssss.bysj.activity;

import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;

public class MatchActivity extends BaseActivity {

    @Override
    protected void findViews() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    /**
     * Cancel matching
     */
    public void cancelMatch(View view) {
        finish();
    }
}
