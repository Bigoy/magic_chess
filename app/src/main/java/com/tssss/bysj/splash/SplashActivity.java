package com.tssss.bysj.splash;

import android.os.Bundle;
import android.util.Log;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.main.MainActivity;
import com.tssss.bysj.splash.presenter.SplashAdPresenter;

@ViewInject(layoutId = R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements ISplashContract.IView {
    private SplashAdPresenter mAdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected void afterBindView() {
        initAdPresenter();
    }

    private void initAdPresenter() {
        mAdPresenter = new SplashAdPresenter(this);
        mAdPresenter.loadAd();
    }

    @Override
    protected boolean requestFullScreen() {
        return true;
    }

    @Override
    public void showAd() {
        Log.i(getClass().getSimpleName(), "暂无广告提供方");
    }

    @Override
    public void skipAd() {
        openActivityAndFinishSelf(MainActivity.class);
    }
}
