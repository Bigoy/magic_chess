package com.tssss.bysj.other.jmessage.callback;

import com.tssss.bysj.game.core.other.GameRole;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

public interface IGetAllFriendsCallBack {
    void onSuccess(List<UserInfo> friendsUserInfoList);

    void onFailure(String errorMsg);
}
