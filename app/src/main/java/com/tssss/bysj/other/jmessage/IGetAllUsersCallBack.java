package com.tssss.bysj.other.jmessage;

import com.tssss.bysj.game.core.other.GameRole;

import java.util.List;

public interface IGetAllUsersCallBack {
    void onSuccess(List<GameRole> gameRoleList);

    void onFailure(String errorMsg);
}
