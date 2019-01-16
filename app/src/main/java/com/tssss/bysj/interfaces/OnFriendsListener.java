package com.tssss.bysj.interfaces;


import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public interface OnFriendsListener {
    void showNullFriends();

    void showFriends(List<GameRole> friends);
}
