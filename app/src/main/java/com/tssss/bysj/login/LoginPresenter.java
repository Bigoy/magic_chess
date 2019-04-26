package com.tssss.bysj.login;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.core.GameRole;
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

import androidx.annotation.NonNull;
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
    public void confirmAccountOperation(String account, String password) {
        int aa = 0;
        if (aa == 0) {
            JMessageClient.login(account, password, new RequestCallback<List<DeviceInfo>>() {
                @Override
                public void gotResult(int i, String s, List<DeviceInfo> deviceInfos) {
                    Logger.log(i + "  " + s);

                    if (i == 0) {
                        AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                        UserInfo userInfo = JMessageClient.getMyInfo();
                        if (null != userInfo) {
                            String roleInfo = userInfo.getSignature();
                            if (!StringUtil.isBlank(roleInfo)) {
                                Map<String, String> roleMap = (Map<String, String>) JSON.parse(roleInfo);

                                GameRole myRole = new GameRole();
                                myRole.setUser(user);
                                myRole.setAvatar(roleMap.get(Constant.ROLE_AVATAR));
                                myRole.setName(roleMap.get(Constant.ROLE_NICK_NAME));
                                myRole.setSex(roleMap.get(Constant.ROLE_SEX));
                                myRole.setSignature(roleMap.get(Constant.ROLE_SIGNATURE));
                                myRole.setLevel(roleMap.get(Constant.ROLE_LEVEL));
                                try {
                                    myRole.setRoleExperience(Integer.valueOf(roleMap.get(Constant.ROLE_EXP)));
                                } catch (Exception e) {
                                    myRole.setRoleExperience(0);
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
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().onNullRoleInfo(user);
                                }
                            });
                        }
                        /*if (userInfo.getExtras().size() <= 0 || StringUtil.isBlank(extrasInfo)) {
                            GameRole role = AppDataCache.readRole();
                            if (null == role) {
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
                                        getView().onSuccess(user, AppDataCache.readRole());
                                    }
                                });

                            }

                        } else {

                            GameRole gameRole = new GameRole();
                            gameRole.setUser(user);
                            gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_AVATAR));
                            gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_NICK_NAME));
                            gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_SEX));
                            gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_SIGNATURE));
                            gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_LEVEL));
                            UserDataCache.saveAccount(user);
                            UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                            AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().onSuccess(user, gameRole);
                                }
                            });

                        }*/
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
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getView().onNullRoleInfo(user);
                                                }
                                            });
                                        }
                                    });
                                    /*UserInfo userInfo = JMessageClient.getMyInfo();
                                    if (userInfo.getExtras().size() <= 0) {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                getView().onAccountNotFound(user);
                                            }
                                        });

                                    } else {
                                        GameRole gameRole = new GameRole();
                                        gameRole.setUser(user);
                                        gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_AVATAR));
                                        gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_NICK_NAME));
                                        gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_SEX));
                                        gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_SIGNATURE));
                                        gameRole.setAvatar(userInfo.getExtra(Constant.ROLE_LEVEL));
                                        UserDataCache.saveAccount(user);
                                        UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                        AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                getView().onSuccess(user, gameRole);
                                            }
                                        });

                                    }*/

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
                        /*handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().onAccountNotFound(user);
                            }
                        });*/
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
            /*Map<String, String> userMap = new HashMap<>();
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
                                JSONObject roleJson = realLoginResult.getJSONObject(Constant.JSON_KEY_ROLE);
                                GameRole role = new GameRole();
                                role.setAvatar(roleJson.getString(Constant.ROLE_AVATAR));
                                role.setName(roleJson.getString(Constant.ROLE_NICK_NAME));
                                role.setSex(roleJson.getString(Constant.ROLE_SEX));
                                role.setSignature(roleJson.getString(Constant.ROLE_SIGNATURE));
                                role.setLevel(roleJson.getString(Constant.ROLE_LEVEL));
                                JMessageClient.login(user.getUserId(), user.getUserPassword(), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        Logger.log(isRunInUIThread());
                                        // 0 表示JMessage登录成功
                                        if (i == 0) {
                                            UserDataCache.saveAccount(user);
                                            UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                            AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    getView().onSuccess(user, role);
                                                }
                                            });
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
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        GameRole debugRole = new GameRole();
                                        debugRole.setAvatar("");
                                        debugRole.setName("debug");
                                        debugRole.setSex(Constant.ROLE_SEX_MAN);
                                        debugRole.setSignature("");
                                        debugRole.setLevel(Constant.ROLE_LEVEL);
                                        getView().onSuccess(user, debugRole);
                                    }
                                });
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
            });*/
        }
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

