package com.tssss.bysj.game.friend;

import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.widget.GTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendHolder extends RecyclerView.ViewHolder {
    private GTextView mFriendNameGtv,
            mFriendStateGtv,
            mFriendRankGtv;


    public FriendHolder(@NonNull View itemView) {
        super(itemView);

        findViews();
    }

    private void findViews() {
        mFriendNameGtv = itemView.findViewById(R.id.item_friend_name_gtv);
        mFriendStateGtv = itemView.findViewById(R.id.item_friend_state_gtv);
        mFriendRankGtv = itemView.findViewById(R.id.item_friend_rank_gtv);
    }

    public void setViews(GameRole friend) {
        mFriendNameGtv.setText(friend.getRoleName());
        mFriendStateGtv.setText(friend.getRoleState());
    }
}
