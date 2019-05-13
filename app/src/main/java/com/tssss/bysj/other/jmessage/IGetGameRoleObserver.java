package com.tssss.bysj.other.jmessage;

import com.tssss.bysj.game.core.other.GameRole;

public interface IGetGameRoleObserver {
    void onComplete(GameRole role);

    void onFailure(String errorMsg);
}
