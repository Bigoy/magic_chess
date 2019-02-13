package com.tssss.bysj.login;

import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AccountUtil;

public class LoginPresenter extends BaseMvpPresenter<ILoginActivityContract.IView> implements ILoginActivityContract.IPresenter {

    public LoginPresenter(ILoginActivityContract.IView view) {
        super(view);
    }

    @Override
    public void identifyAccount(User user) {
        if (!AccountUtil.validPhoneNumber(user.getUserId())) {
            if (getView() instanceof LoginActivity) {
                ((LoginActivity) getView()).onInvalidPhoneNumber();
            }
        }
    }

    @Override
    public void login() {

    }

    @Override
    protected IMvpView getEmptyView() {
        return ILoginActivityContract.emptyView;
    }
}
