package com.tssss.bysj.login;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;

public class LoginPresenter extends PresenterImp implements OnLoginDataListener {
    private LoginModel mModel;
    private OnLoginListener mLoginListener;

    public LoginPresenter() {
        mModel = new LoginModel();
    }

    public void requestLogin(User user, OnLoginListener listener) {
        mLoginListener = listener;
        mModel.loadUserData(user, this);
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
