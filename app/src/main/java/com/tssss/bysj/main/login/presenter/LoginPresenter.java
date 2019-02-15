package com.tssss.bysj.main.login.presenter;

import com.tssss.bysj.main.login.ILoginFmContract;
import com.tssss.bysj.main.login.model.LoginHttpTask;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.http.result.IResult;
import com.tssss.mvp.base.BaseMvpPresenter;

public class LoginPresenter extends BaseMvpPresenter<ILoginFmContract.IView> implements ILoginFmContract.IPresenter {

    public LoginPresenter(ILoginFmContract.IView view) {
        super(view);
    }

    @Override
    public void identifyAccount(User user) {
        if (!AccountUtil.validPhoneNumber(user.getUserId()))
            getView().onInvalidPhoneNumber();

        if (!AccountUtil.validPassword(user.getUserPassword()))
            getView().onInvalidPassword();

        if (AccountUtil.validPhoneNumber(user.getUserId()))
            getView().onValidPhoneNumber();

        if (AccountUtil.validPassword(user.getUserPassword()))
            getView().onValidPassword();

        if (AccountUtil.validAccount(user.getUserId(), user.getUserPassword())) {
            getView().onValidAccount();
            login();
        }
    }

    @Override
    public void login() {
        new Thread(() -> {
            IResult result = new LoginHttpTask<>().test();
        }).start();
    }

    @Override
    protected ILoginFmContract.IView getEmptyView() {
        return ILoginFmContract.emptyView;
    }

}
