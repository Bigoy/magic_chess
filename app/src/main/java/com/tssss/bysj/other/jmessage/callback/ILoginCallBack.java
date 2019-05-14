package com.tssss.bysj.other.jmessage.callback;

import com.tssss.bysj.game.core.other.GameRole;

public interface ILoginCallBack {

    void onLoginSuccess(GameRole myRole);

    void onNullRole();

    void onPasswordError();

    void onAutoRegister();

    void onLoginFailed(String errorMsg);
}
