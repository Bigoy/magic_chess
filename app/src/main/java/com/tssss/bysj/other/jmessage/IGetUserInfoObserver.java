package com.tssss.bysj.other.jmessage;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

public interface IGetUserInfoObserver {
    void onCompleted(List<UserInfo> userInfoList);
}
