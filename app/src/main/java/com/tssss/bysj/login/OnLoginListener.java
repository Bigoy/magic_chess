package com.tssss.bysj.login;

public interface OnLoginListener {
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
