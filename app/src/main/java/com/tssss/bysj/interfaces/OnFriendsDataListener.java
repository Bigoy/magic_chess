package com.tssss.bysj.interfaces;


import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public interface OnFriendsDataListener {
    void onFriendsNumber(int friends);

    void onFriends(List<GameRole> friends);
}
