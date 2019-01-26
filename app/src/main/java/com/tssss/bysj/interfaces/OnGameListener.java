package com.tssss.bysj.interfaces;

import com.tssss.bysj.user.role.GameRole;

public interface OnGameListener {
    void onComplete(GameRole self, GameRole rival);

    void onFailure();
}
