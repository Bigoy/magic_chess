package com.tssss.bysj.main.home;

import com.tssss.mvp.ILifeCircle;
import com.tssss.mvp.IMvpView;
import com.tssss.mvp.MvpController;

import androidx.fragment.app.Fragment;

public interface IHomeFmContract {
    interface IView extends IMvpView {
        void switchFragmentPage(int container, Fragment fragment);
    }

    interface IPresenter extends ILifeCircle {
        void replaceFragment(int container, Fragment fragment);
    }

    IView emptyView = new IView() {
        @Override
        public void switchFragmentPage(int container, Fragment fragment) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
