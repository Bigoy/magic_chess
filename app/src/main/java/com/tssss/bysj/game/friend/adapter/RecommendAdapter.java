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
import com.tssss.bysj.game.core.GameRole;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private Context context;
    private List<GameRole> gameRoleList;

    public RecommendAdapter(Context context, List<GameRole> gameRoleList) {
        this.context = context;
        this.gameRoleList = gameRoleList;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
        if (null != gameRoleList) {
            holder.fillData(gameRoleList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return gameRoleList.size();
    }

    public static class RecommendViewHolder extends BaseRvViewHolder<GameRole> {
        private ImageView avatar;
        private GTextView name;
        private GTextView level;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        protected void instantiateObject() {

        }

        @Override
        public void fillData(GameRole data) {
            if (null != data) {
                name.setText(data.getName());
                level.setText(data.getLevel());
                JMessageClient.getUserInfo(data.getUser().getUserId(), new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0) {
                            Glide.with(getContext())
                                    .load(userInfo.getAvatarFile())
                                    .into(avatar);

                        }

                    }
                });
            }
        }

        @Override
        protected void findViews() {
            avatar = findImageView(R.id.item_recommend_avatar);
            name = findGTextView(R.id.item_recommend_name);
            level = findGTextView(R.id.item_recommend_level);
        }
    }
}
