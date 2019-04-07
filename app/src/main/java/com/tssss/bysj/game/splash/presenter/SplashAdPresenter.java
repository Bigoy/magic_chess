package com.tssss.bysj.game.splash.presenter;

import android.os.Handler;

import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.game.splash.ISplashContract;

public class SplashAdPresenter extends BaseMvpPresenter<ISplashContract.IView> implements ISplashContract.IPresenter {

    private Handler handler;

    public SplashAdPresenter(ISplashContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    protected ISplashContract.IView getEmptyView() {
        return ISplashContract.emptyView;
    }

    public void loadAd() {
        // 暂时未接入广告 所以方法为空
        getView().showAd();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().skipAd();
            }
        }, 2000);
    }

}
