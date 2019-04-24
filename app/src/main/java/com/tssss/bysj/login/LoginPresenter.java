package com.tssss.bysj.login;

import android.content.Context;
import android.os.Handler;

import com.tssss.bysj.http.HttpCallback;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginPresenter extends BaseMvpPresenter<IAccountContract.IView>
        implements IAccountContract.IPresenter {

    private String TAG = this.getClass().getSimpleName();

    private User user;
    private boolean cancelLogin;
    private Handler handler;

    LoginPresenter(Context context, IAccountContract.IView view) {
        super(view);
        user = new User();
        handler = new Handler();
    }

    @Override
    public void verifyAccountFormat(User user) {
        UserDataCache.saveAccount(user);
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
            getView().onProcess();
        }
    }

    /**
     * 执行用户的用户操作
     * 向服务器发起登录请求
     */
    @Override
    public void confirmAccountOperation() {
        if (null != user) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("userID", user.getUserId());
            userMap.put("userPassword", user.getUserPassword());
            OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_LOGIN, userMap, new HttpCallback() {

                @Override
                public void onSuccess(String result) {
                    if (!cancelLogin) {
                        try {
                            JSONObject realLoginResult = new JSONObject(result);
                            String finalLoginState = realLoginResult.getString(Constant.LOGIN_STATE);
                            if (Constant.LOGIN_STATE_SUCCESS.equals(finalLoginState)) {
                                JMessageClient.login(user.getUserId(), user.getUserPassword(), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        Logger.log(isRunInUIThread());
                                        // 0 表示JMessage登录成功
                                        if (i == 0) {
                                            UserDataCache.saveAccount(user);
                                            UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                            AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                                            updateUi(Constant.LOGIN_STATE_SUCCESS);
                                        } else {
                                            Logger.log(s);
                                            updateUi(Constant.LOGIN_STATE_FAILED);
                                        }
                                    }
                                });
                            } else if (Constant.LOGIN_STATE_NOT_REGISTER.equals(finalLoginState)) {
                                updateUi(Constant.LOGIN_STATE_NOT_REGISTER);
                            } else if (Constant.LOGIN_STATE_ERROR_PASSWORD.equals(finalLoginState)) {
                                updateUi(Constant.LOGIN_STATE_ERROR_PASSWORD);
                            } else {
                                updateUi(Constant.LOGIN_STATE_FAILED);
                                Logger.log("服务端出现了问题!");
                            }
                        } catch (JSONException e) {
                            if (!Constant.DEBUG) {
                                Logger.log("json 对象解析异常，请检查服务端返回的内容！");
                                updateUi(Constant.LOGIN_STATE_FAILED);

                            } else {
                                UserDataCache.saveAccount(user);
                                UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                                updateUi(Constant.LOGIN_STATE_SUCCESS);
                            }
                        }

                    } else {
                        Logger.log("登录取消");
                    }
                }

                @Override
                public void onFailure(String errorMsg) {
                    if (!cancelLogin) {
                        updateUi(Constant.LOGIN_STATE_FAILED);
                    } else {
                        Logger.log("登录取消");
                    }
                }
            });
        }
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
        if (!cancelLogin) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (Constant.LOGIN_STATE_SUCCESS.equals(type)) {
                        getView().onSuccess(user);
                    } else if (Constant.LOGIN_STATE_FAILED.equals(type)) {
                        getView().onError(Constant.NET_CODE_UNKNOWN, "登录异常");
                    } else if (Constant.LOGIN_STATE_NOT_REGISTER.equals(type)) {
                        getView().onAccountNotFound(user);
                    } else if (Constant.LOGIN_STATE_ERROR_PASSWORD.equals(type)) {
                        getView().onPasswordError();
                    }
                }
            });

        }
    }
}

