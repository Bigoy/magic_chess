package com.tssss.bysj.other.jmessage.callback;

import com.tssss.bysj.game.core.other.GameRole;

public interface IGetGameRoleCallBack {
    void onComplete(GameRole role);

    void onFailure(String errorMsg);
}
