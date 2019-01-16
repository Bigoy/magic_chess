package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnRegisterListener;

public class RegisterPresenter extends PresenterImp {
    public void requestRegister(long userId, String password, OnRegisterListener listener) {
        listener.onFail();
    }
}
