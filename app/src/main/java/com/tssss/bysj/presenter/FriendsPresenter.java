package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnFriendsDataListener;
import com.tssss.bysj.interfaces.OnFriendsListener;
import com.tssss.bysj.model.FriendsModel;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public class FriendsPresenter extends PresenterImp implements OnFriendsDataListener {
    private FriendsModel mFriendsModel;
    private OnFriendsListener mListener;

    /**
     * Load friends data of the user from FriendModel.
     */
    public void loadFriends(User user, OnFriendsListener listener) {
        this.mListener = listener;

        mFriendsModel = new FriendsModel();
        mFriendsModel.loadFriendsData(user, this);
    }

    @Override
    public void onComplete(List<GameRole> friends) {
        if (friends.size() == 0)
            mListener.onNullFriends();
        else
            mListener.onComplete(friends);
    }

    @Override
    public void onFailure() {
        mListener.onFailure();
    }
}
