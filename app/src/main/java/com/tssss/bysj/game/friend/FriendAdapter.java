package com.tssss.bysj.game.friend;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private Context context;
    private List<GameRole> gameRoleList;
    private OnFriendItemClickListener listener;

    public FriendAdapter(Context context, List<GameRole> gameRoleList, OnFriendItemClickListener listener) {
        this.context = context;
        this.gameRoleList = gameRoleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.fillData(gameRoleList.get(position));
        holder.setListeners(this.listener, position);
    }

    @Override
    public int getItemCount() {
        return gameRoleList.size();
    }

    public static class FriendViewHolder extends BaseRvViewHolder<GameRole> {
        private GTextView name;
        private ImageView avatar;
        private GTextView level;
        private LinearLayout container;
        private Handler handler;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {
            handler = new Handler();
        }

        @Override
        public void fillData(GameRole data) {
            if (null != data) {
                name.setText(data.getName());
                Glide.with(getContext())
                        .load(data.getAvatarFile())
                        .into(avatar);
                String levelStr = data.getLevel();
                if (StringUtil.isBlank(levelStr)) {
                    levelStr = Constant.ROLE_SX;
                }
                level.setText(levelStr);
            }
        }

        @Override
        protected void findViews() {
            name = findGTextView(R.id.item_friend_name);
            avatar = findImageView(R.id.item_friend_avatar);
            level = findGTextView(R.id.item_friend_level);
            container = findLinearLayout(R.id.item_friend_container);

        }

        public void setListeners(OnFriendItemClickListener listeners, int position) {
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listeners) {
                        AnimationUtil.startBackgroundColorAnimator(container, 0x00000000, 0xffB79489);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listeners.onClick(v, position);
//                                AnimationUtil.startBackgroundColorAnimator(container, 0xffB79489, 0x00000000);
                            }
                        }, 110);
                    }
                }
            });
        }
    }
}
