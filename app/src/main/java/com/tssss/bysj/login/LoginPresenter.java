package com.tssss.bysj.login;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.StringUtil;

import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.UserInfo;
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
        JMessageClient.login(account, password, new RequestCallback<List<DeviceInfo>>() {
            @Override
            public void gotResult(int i, String s, List<DeviceInfo> deviceInfos) {
                Logger.log(i + "  " + s);

                if (i == 0) {
                    // 保存用户输入的账户和密码 账户与密码匹配
                    UserDataCache.saveAccount(new User(account, password));
                    AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                    UserInfo userInfo = JMessageClient.getMyInfo();
                    String roleInfo = userInfo.getSignature();
                    if (!StringUtil.isBlank(roleInfo)) {
                        Map<String, String> roleMap = (Map<String, String>) JSON.parse(roleInfo);

                        GameRole myRole = new GameRole();
                        myRole.setUser(user);
                        myRole.setAvatarStr(roleMap.get(Constant.ROLE_AVATAR));
                        myRole.setName(roleMap.get(Constant.ROLE_NICK_NAME));
                        myRole.setSex(roleMap.get(Constant.ROLE_SEX));
                        myRole.setSignature(roleMap.get(Constant.ROLE_SIGNATURE));
                        myRole.setLevel(roleMap.get(Constant.ROLE_LEVEL));
                        try {
                            myRole.setRoleExperience(Integer.valueOf(roleMap.get(Constant.ROLE_EXP)));
                            myRole.setScore(Integer.valueOf(roleMap.get(Constant.ROLE_SCORE)));
                        } catch (Exception e) {
                            myRole.setRoleExperience(0);
                            myRole.setScore(0);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().onSuccess(user, myRole);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().onNullRoleInfo(user);
                            }
                        });
                    }

                } else if (i == 871105 || i == 898002 || i == 801003) {
                    // 用户不存在
                    JMessageClient.register(user.getUserId(), user.getUserPassword(), new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            Logger.log(i + s);
                            if (i == 0) {
                                JMessageClient.login(user.getUserId(), user.getUserPassword(), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        if (i == 0) {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getView().onNullRoleInfo(user);
                                                }
                                            });
                                        } else {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getView().onError(i, s);
                                                }
                                            });
                                        }
                                    }
                                });


                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        getView().onError(i, s);
                                    }
                                });
                            }
                        }
                    });

                } else if (i == 871304) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().onPasswordError();
                        }
                    });

                } else if (i == 801004) {
                    Logger.log(i + s);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().onPasswordError();
                        }
                    });
                }
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
//                        getView().onSuccess(user);
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

