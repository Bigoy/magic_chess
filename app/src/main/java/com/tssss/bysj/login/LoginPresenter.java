package com.tssss.bysj.login;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.tssss.bysj.http.Callback;
import com.tssss.bysj.http.HttpClient;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.UserDataCache;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

public class  LoginPresenter extends BaseMvpPresenter<IAccountContract.IView>
        implements IAccountContract.IPresenter {

    private String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Handler handler;
    private User user;
    private boolean cancelLogin;

    public LoginPresenter(Context context,IAccountContract.IView view) {
        super(view);
        handler = new Handler();
        user = new User();
        mContext = context;
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
        updateUi("SUCCESS");
        /*HttpClient client = new HttpClient();
        client.getAsync(Constant.BASE_URL, new Callback() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String haveUser = jo.getString(Constant.JSON_KEY_ACCOUNT_EXIST);
                    if (Constant.ACCOUNT_FOUND.equalsIgnoreCase(haveUser)) {
                        String canAccess = jo.getString(Constant.JSON_KEY_ACCOUNT_ACCESSIBLE);
                        if (Constant.ACCOUNT_ACCESSIBLE_DENY.equalsIgnoreCase(canAccess)) {
                            updateUi("ERROR_PASSWORD");
                        }else if (Constant.ACCOUNT_ACCESSIBLE_GRANT.equalsIgnoreCase(canAccess)) {
                            user.setUserId(jo.getString(Constant.JSON_KEY_ACCOUNT));
                            user.setUserPassword(jo.getString(Constant.JSON_KEY_ACCOUNT_PASSWORD));
                            updateUi("SUCCESS");
                            UserDataCache.saveAccount(mContext, user);
                        }
                    }else {
                        updateUi(Constant.ACCOUNT_NOT_FOUND);
                    }
                } catch (JSONException e) {
                    updateUi("WRONG");
                }
            }

            @Override
            public void onFailure() {
                updateUi("WRONG");
            }
        });*/
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
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (Constant.ACCOUNT_NOT_FOUND.equalsIgnoreCase(type)) {
                        getView().onAccountNotFound();
                    }else if ("WRONG".equalsIgnoreCase(type)) {
                        getView().onConnectionFailure(Constant.NET_CODE_UNKNOWN);
                    }else if ("SUCCESS".equalsIgnoreCase(type)) {
                        getView().onSuccess(user);
                    }else if ("ERROR_PASSWORD".equalsIgnoreCase(type)) {
                        getView().onPasswordError();

                    }
                }
            };
            handler.post(r);
        }
    }
}
