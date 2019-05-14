package com.tssss.bysj.login;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.jmessage.JMessageManager;
import com.tssss.bysj.other.jmessage.callback.ILoginCallBack;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AccountUtil;

public class LoginPresenter extends BaseMvpPresenter<IAccountContract.IView>
        implements IAccountContract.IPresenter {

    private User user;
    private Handler handler;

    LoginPresenter(IAccountContract.IView view) {
        super(view);
        user = new User();
        handler = new Handler();
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
            this.user = user;
            // 保存用户输入的账户和密码 不管账户与密码是否匹配
            UserDataCache.saveAccount(user);
            getView().onProcess();
        }
    }

    /**
     * 执行用户的用户操作
     * 向服务器发起登录请求
     */
    @Override
    public void confirmAccountOperation(String account, String password) {
        JMessageManager.login(account, password, new ILoginCallBack() {
            @Override
            public void onLoginSuccess(GameRole myRole) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().onSuccess();
                    }
                });
                UserDataCache.saveAccount(new User(account, password));
                UserDataCache.keepRole(myRole);
                AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
            }

            @Override
            public void onNullRole() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().onNullRoleInfo(user);
                    }
                });
            }

            @Override
            public void onPasswordError() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().onPasswordError();
                    }
                });
            }

            @Override
            public void onAutoRegister() {

            }

            @Override
            public void onLoginFailed(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().onError(errorMsg);
                    }
                });
            }
        });

    }

    @Override
    public void returnUser(@NonNull User user) {
//        getView().onSuccess(user);
    }

    @Override
    protected IAccountContract.IView getEmptyView() {
        return (IAccountContract.IView) IAccountContract.emptyView;
    }

}

