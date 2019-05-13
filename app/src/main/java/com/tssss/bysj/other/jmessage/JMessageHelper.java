package com.tssss.bysj.other.jmessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.IHttpCallback;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class JMessageHelper {

    public static GameRole toGameRole(UserInfo userInfo) {
        if (null != userInfo) {
            String roleInfoJsonStr = userInfo.getSignature();
            GameRole role = toGameRoleWithoutAvatarFile(roleInfoJsonStr);
            role.setAvatarFile(userInfo.getAvatarFile());
            return role;
        }
        return new GameRole();
    }

    public static GameRole toGameRoleWithoutAvatarFile(String roleInfoJsonStr) {
        com.alibaba.fastjson.JSONObject roleInfoJson = JSON.parseObject(roleInfoJsonStr);
        GameRole role = new GameRole();
        role.setUser(new User(roleInfoJson.getString(Constant.ACCOUNT_ID), ""));
        role.setName(roleInfoJson.getString(Constant.ROLE_NICK_NAME));
        role.setSex(roleInfoJson.getString(Constant.ROLE_SEX));
        role.setSignature(Constant.ROLE_SIGNATURE);
        role.setLevel(Constant.ROLE_LEVEL);
        try {
            role.setExp(Integer.valueOf(roleInfoJson.getString(Constant.ROLE_EXP)));
            role.setScore(Integer.valueOf(roleInfoJson.getString(Constant.ROLE_SCORE)));
        } catch (Exception e) {
            role.setExp(0);
            role.setScore(0);
        }
        return role;
    }

    public static void toGameRole(String roleInfoJsonStr, IGetGameRoleObserver observer) {
        if (null != observer) {
            GameRole role = toGameRoleWithoutAvatarFile(roleInfoJsonStr);
            String accountID = role.getUser().getUserId();
            JMessageClient.getUserInfo(accountID, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        role.setAvatarFile(userInfo.getAvatarFile());
                        observer.onComplete(role);
                    } else {
                        String errorMsg = "获取头像失败:" + s;
                        observer.onFailure(errorMsg);
                    }
                }
            });
        }
    }

    public static void getGameRoleByAccountID(String accountID, IGetGameRoleObserver observer) {
        if (null != observer) {
            if (StringUtil.isBlank(accountID)) {
                throw new IllegalArgumentException("AccountID can not be null");
            }
            JMessageClient.getUserInfo(accountID, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        GameRole role = toGameRole(userInfo);
                        observer.onComplete(role);
                    } else {
                        String errorMsg = "获取游戏角色信息失败" + s;
                        observer.onFailure(errorMsg);
                    }
                }
            });
        }
    }

    public static void listAllGameRoles(IGetAllUsersCallBack callBack) {
        if (null != callBack) {
            List<GameRole> gameRoleList = new ArrayList<>();
            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("start", 0);
            paramMap.put("count", 100);
            OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_IM_USER, paramMap, new IHttpCallback() {
                public void onSuccess(String result) {
                    com.alibaba.fastjson.JSONObject resultJson = JSON.parseObject(result);
                    JSONArray usersJsonArray = resultJson.getJSONArray("users");
                    GetUserInfoCallBackImpl userInfoCallBack = new GetUserInfoCallBackImpl(usersJsonArray.size());
                    for (int i = 0; i < usersJsonArray.size(); i++) {
                        String accountID = usersJsonArray.getJSONObject(i).getString("username");
                        JMessageClient.getUserInfo(accountID, userInfoCallBack);
                    }
                    userInfoCallBack.setIGetUserInfoObserver(userInfoList -> {
                        for (int k = 0; k < userInfoList.size(); k++) {
                            UserInfo userInfo = userInfoList.get(k);
                            gameRoleList.add(toGameRole(userInfo));

                        }
                        callBack.onSuccess(gameRoleList);
                    });

                }

                @Override
                public void onFailure(String errorMsg) {
                    callBack.onFailure(errorMsg);
                    Logger.log("获取用户列表失败:" + errorMsg);
                }
            });
        }
    }

}
