package com.tssss.bysj.interfaces;


import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public interface OnFriendsDataListener {
    void onComplete(List<GameRole> friends);

    void onFailure();
}
