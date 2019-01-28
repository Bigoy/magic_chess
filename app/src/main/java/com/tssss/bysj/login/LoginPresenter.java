package com.tssss.bysj.login;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;
import com.tssss.bysj.util.AccountUtil;

public class LoginPresenter extends PresenterImp implements OnLoginDataListener {
    private LoginModel mModel;
    private OnLoginListener mLoginListener;

    LoginPresenter() {
        mModel = new LoginModel();
    }

    void requestLogin(User user, OnLoginListener listener) {
        mLoginListener = listener;

        if (!AccountUtil.validPhoneNumber(user.getUserId()))
            mLoginListener.onInvalidPhoneNumber();

        if (!AccountUtil.validPassword(user.getUserPassword()))
            mLoginListener.onInvalidPassword();

        if (AccountUtil.validPhoneNumber(user.getUserId()))
            mLoginListener.onValidPhoneNumber();

        if (AccountUtil.validPassword(user.getUserPassword()))
            mLoginListener.onValidPassword();

        if (AccountUtil.validAccount(user.getUserId(), user.getUserPassword())) {
            mLoginListener.onValidAccount();
            mModel.loadUserData(user, this);
        }
    }

    @Override
    public void onLoadDataCompleted(GameRole gameRole) {
        GameRoleManager roleManager = GameRoleManager.getGameRoleManager();
        roleManager.addRole(GameRoleManager.SELF, gameRole);

        mLoginListener.onLoginSuccess();
    }

    @Override
    public void onUserNotExit() {
        mLoginListener.onUserNotExit();
    }

    @Override
    public void onUserPasswordError() {
        mLoginListener.onUserPasswordError();
    }

    @Override
    public void onFailure() {
        mLoginListener.onLoginError();
    }
}
