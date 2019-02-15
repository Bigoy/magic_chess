package com.tssss.bysj.main.login;

import com.tssss.bysj.user.User;
import com.tssss.mvp.ILifeCircle;
import com.tssss.mvp.IMvpView;
import com.tssss.mvp.MvpController;

public interface ILoginFmContract {
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
