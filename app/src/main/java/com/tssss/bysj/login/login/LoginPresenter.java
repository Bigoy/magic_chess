package com.tssss.bysj.login.login;

import android.util.Log;

import com.tssss.bysj.http.annoation.RequestMethod;
import com.tssss.bysj.http.okhttp.OkHttpScheduler;
import com.tssss.bysj.http.request.BaiduRequest;
import com.tssss.bysj.http.response.IResponse;
import com.tssss.bysj.login.IAccountContract;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
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

    @Override
    public void confirmAccountOperation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpScheduler okHttpScheduler = new OkHttpScheduler();
                IResponse response = okHttpScheduler.newCall(BaiduRequest.sendHttp("",
                        RequestMethod.GET)).execute();
                Log.wtf("result", response.getBodyString());
            }
        }).start();
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
