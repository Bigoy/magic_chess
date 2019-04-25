package com.tssss.bysj.game.friend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.Role;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private Context context;
    private List<Role> roleList;

    public RecommendAdapter(Context context, List<Role> roleList) {
        this.context = context;
        this.roleList = roleList;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
        if (null != roleList) {
            holder.fillData(roleList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return roleList.size();
    }

    public static class RecommendViewHolder extends BaseRvViewHolder<Role> {
        private ImageView avatar;
        private GTextView name;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {

        }

        @Override
        public void fillData(Role data) {
            if (null != data) {
                Glide.with(getContext())
                        .load(data.getAvatar())
                        .into(avatar);
                name.setText(data.getName());

            }
        }

        @Override
        protected void findViews() {
            avatar = findImageView(R.id.item_recommend_avatar);
            name = findGTextView(R.id.item_recommend_name);
        }
    }
}
