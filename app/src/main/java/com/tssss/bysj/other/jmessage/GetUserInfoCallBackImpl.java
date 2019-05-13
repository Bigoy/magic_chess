package com.tssss.bysj.other.jmessage;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class GetUserInfoCallBackImpl extends GetUserInfoCallback {
    private List<UserInfo> userInfoList;
    private int count;
    private int gotUserInfoCount;
    private IGetUserInfoObserver iGetUserInfoObserver;

    public GetUserInfoCallBackImpl(int count) {
        userInfoList = new ArrayList<>();
        this.count = count;
    }

    public void setIGetUserInfoObserver(IGetUserInfoObserver iGetUserInfoObserver) {
        this.iGetUserInfoObserver = iGetUserInfoObserver;
    }

    @Override
    public void gotResult(int i, String s, UserInfo userInfo) {
        gotUserInfoCount++;
        if (i == 0) {
            userInfoList.add(userInfo);
            if (gotUserInfoCount == count) {
                if (null != iGetUserInfoObserver) {
                    iGetUserInfoObserver.onCompleted(userInfoList);
                    gotUserInfoCount = 0;
                }
            }
        }
    }
}
