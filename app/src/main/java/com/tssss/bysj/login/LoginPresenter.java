package com.tssss.bysj.login;

import android.content.Context;

import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AccountUtil;

import androidx.annotation.NonNull;

public class LoginPresenter extends BaseMvpPresenter<IAccountContract.IView>
        implements IAccountContract.IPresenter {

    private String TAG = this.getClass().getSimpleName();

    private User user;
    private boolean cancelLogin;

    LoginPresenter(Context context, IAccountContract.IView view) {
        super(view);
        user = new User();
    }

    @Override
    public void verifyAccountFormat(User user) {
        if (!AccountUtil.validPhoneNumber(user.getUserId())) {
            getView().onAccountFormatError();
        }
        if (!AccountUtil.validPassword(user.getUserPassword())) {
            getView().onPasswordFormatError();
        }
        if (AccountUtil.validPhoneNumber(user.getUserId())) {
            getView().onValidAccount();
        }
        if (AccountUtil.validPassword(user.getUserPassword())) {
            getView().onValidPassword();
        }
        if (AccountUtil.validAccount(user.getUserId(), user.getUserPassword())) {
            getView().onProcess();
        }
    }

    /**
     * 执行用户的用户操作
     * 向服务器发起登录请求
     */
    @Override
    public void confirmAccountOperation() {
//        new LoginTask().execute(user);
        getView().onSuccess(user);
    }

    @Override
    public void returnUser(@NonNull User user) {
        getView().onSuccess(user);
    }

    @Override
    protected IAccountContract.IView getEmptyView() {
        return (IAccountContract.IView) IAccountContract.emptyView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelLogin = true;
    }

    /**
     * 更新相应的界面状态，显示给用户
     */
    private void updateUi(String type) {
    }
}
