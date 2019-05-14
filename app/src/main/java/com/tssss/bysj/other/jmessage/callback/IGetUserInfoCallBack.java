package com.tssss.bysj.other.jmessage.callback;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

public interface IGetUserInfoCallBack {
    void onCompleted(List<UserInfo> userInfoList);
}
