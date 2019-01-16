package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnFriendsDataListener;
import com.tssss.bysj.interfaces.OnFriendsListener;
import com.tssss.bysj.model.FriendsModel;
import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public class FriendsPresenter extends PresenterImp implements OnFriendsDataListener {
    private FriendsModel friendsModel;
    private OnFriendsListener listener;

    public void loadFriends(long userId, OnFriendsListener listener) {
        this.listener = listener;
        friendsModel = new FriendsModel();
        friendsModel.loadFriendsData(userId, this);
    }

    @Override
    public void onFriendsNumber(int friends) {
        if (friends == 0) {
            listener.showNullFriends();
        }
    }

    @Override
    public void onFriends(List<GameRole> friends) {
        listener.showFriends(friends);
    }
}
