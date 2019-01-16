package com.tssss.bysj.model;

import com.tssss.bysj.interfaces.OnFriendsDataListener;
import com.tssss.bysj.user.role.GameRole;

import java.util.ArrayList;
import java.util.List;

public class FriendsModel {
    public void loadFriendsData(long userId, OnFriendsDataListener listener) {
        List<GameRole> friends = new ArrayList<>();
        listener.onFriendsNumber(friends.size());
        listener.onFriends(friends);
    }

}
