package com.tssss.bysj.splash;


import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

public interface ISplashContract {
    interface IView extends IMvpView {
        void showAd();

        void skipAd();
    }

    interface IPresenter extends ILifeCircle {

    }

    IView emptyView = new IView() {
        @Override
        public void showAd() {
            // 空 View 只是为了防止空指针 不做任何操作
        }

        @Override
        public void skipAd() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
