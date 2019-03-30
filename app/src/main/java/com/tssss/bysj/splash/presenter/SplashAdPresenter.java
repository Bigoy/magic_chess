package com.tssss.bysj.splash.presenter;

import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.splash.ISplashContract;

public class SplashAdPresenter extends BaseMvpPresenter<ISplashContract.IView> implements ISplashContract.IPresenter {

    public SplashAdPresenter(ISplashContract.IView view) {
        super(view);
    }

    @Override
    protected ISplashContract.IView getEmptyView() {
        return ISplashContract.emptyView;
    }

    public void loadAd() {
        // 暂时未接入广告 所以方法为空
        getView().showAd();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    getView().skipAd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
