package com.tssss.bysj.interfaces;

import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public interface OnPeopleDataListener {
    void onComplete(List<GameRole> allPlayers);

    void onFailure();
}
