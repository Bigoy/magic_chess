package com.tssss.bysj.other.jmessage.callback;

import com.tssss.bysj.game.core.other.GameRole;

import java.util.List;

public interface IGetAllGameRoleCallBack {
    void onSuccess(List<GameRole> gameRoleList);

    void onFailure(String errorMsg);
}
