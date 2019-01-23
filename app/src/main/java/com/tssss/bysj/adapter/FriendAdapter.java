package com.tssss.bysj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tssss.bysj.R;
import com.tssss.bysj.game.friend.FriendHolder;
import com.tssss.bysj.user.role.GameRole;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendAdapter extends RecyclerView.Adapter {
    private List<GameRole> mFriendsList;
    private Context mContext;


    public FriendAdapter(Context context, List<GameRole> friends) {
        mContext = context;
        mFriendsList = friends;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendHolder(LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendHolder) {
            ((FriendHolder) holder).setViews(mFriendsList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }

}
