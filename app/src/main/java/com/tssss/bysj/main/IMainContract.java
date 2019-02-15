package com.tssss.bysj.main;

import com.tssss.mvp.ILifeCircle;
import com.tssss.mvp.IMvpView;
import com.tssss.mvp.MvpController;

import androidx.fragment.app.Fragment;

public interface IMainContract {
    interface IView extends IMvpView {
        void showFragment(Fragment fragment);

        void hideFragment(Fragment fragment);

        void addFragment(int container, Fragment fragment);

        void replaceFragment(int container, Fragment fragment);

        void removeFragment(Fragment fragment);
    }

    interface IPresenter extends ILifeCircle {
        void initFragment();
    }

    IView emptyView = new IView() {


        @Override
        public void showFragment(Fragment fragment) {

        }

        @Override
        public void hideFragment(Fragment fragment) {

        }

        @Override
        public void addFragment(int container, Fragment fragment) {

        }

        @Override
        public void replaceFragment(int container, Fragment fragment) {

        }

        @Override
        public void removeFragment(Fragment fragment) {

        }


        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
