package com.tssss.bysj.game.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.Role;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private Context context;
    private List<Role> roleList;

    public FriendAdapter(Context context, List<Role> roleList) {
        this.context = context;
        this.roleList = roleList;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.fillData(roleList.get(position));
    }

    @Override
    public int getItemCount() {
        return roleList.size();
    }

    public static class FriendViewHolder extends BaseRvViewHolder<Role> {
        private GTextView name;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {

        }

        @Override
        public void fillData(Role data) {
            if (null != data) {
                name.setText(data.getName());

            }
        }

        @Override
        protected void findViews() {
            name = findGTextView(R.id.item_friend_name);

        }
    }
}
