package com.tssss.bysj.login.login;

import com.tssss.bysj.login.IAccountContract;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AccountUtil;

import androidx.annotation.NonNull;

public class LoginPresenter extends BaseMvpPresenter<IAccountContract.IView>
        implements IAccountContract.IPresenter {

    public LoginPresenter(IAccountContract.IView view) {
        super(view);
    }

    @Override
    public void verifyAccountFormat(@NonNull User user) {
        if (!AccountUtil.validPhoneNumber(user.getUserId())) {
            getView().onAccountFormatError();
        }else if (!AccountUtil.validPassword(user.getUserPassword())) {
            getView().onPasswordFormatError();
        }else {
            confirmAccountOperation();
        }
    }

    @Override
    public void confirmAccountOperation() {
        getView().onProcess();
    }

    @Override
    public void returnUser(@NonNull User user) {
        getView().onSuccess(user);
    }

    @Override
    protected IAccountContract.IView getEmptyView() {
        return (IAccountContract.IView) IAccountContract.emptyView;
    }
}
