package com.tssss.bysj.login;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;
import com.tssss.bysj.user.User;

public interface ILoginActivityContract {
    interface IView extends IMvpView {
        void onLoginSuccess();

        void onLoginError();

        void onUserNotExit();

        void onUserPasswordError();

        void onInvalidPhoneNumber();

        void onInvalidPassword();

        void onValidPhoneNumber();

        void onValidPassword();

        void onValidAccount();
    }

    interface IPresenter extends ILifeCircle {
        void identifyAccount(User user);

        void login();
    }

    IView emptyView = new IView() {
        @Override
        public void onLoginSuccess() {

        }

        @Override
        public void onLoginError() {

        }

        @Override
        public void onUserNotExit() {

        }

        @Override
        public void onUserPasswordError() {

        }

        @Override
        public void onInvalidPhoneNumber() {

        }

        @Override
        public void onInvalidPassword() {

        }

        @Override
        public void onValidPhoneNumber() {

        }

        @Override
        public void onValidPassword() {

        }

        @Override
        public void onValidAccount() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
