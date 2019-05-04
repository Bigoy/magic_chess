package com.tssss.bysj.game.rank;

import android.annotation.SuppressLint;
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
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private SortedList<Rank> rankSortedList;
    private Context context;
    private Rank data;
    private RankViewHolder.OnRankItemClickListener onRankItemClickListener;


    public RankAdapter(Context context, SortedList<Rank> rankSortedList) {
        this.context = context;
        this.rankSortedList = rankSortedList;
    }

    public void setRankSortedList(SortedList<Rank> rankSortedList) {
        this.rankSortedList = rankSortedList;

    }

    public void setOnRankItemClickListener(RankViewHolder.OnRankItemClickListener onRankItemClickListener) {
        this.onRankItemClickListener = onRankItemClickListener;

    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rank, parent, false);
        return new RankViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        data = rankSortedList.get(position);
        data.setRankNum(position + 1);
        holder.fillData(data);
        holder.setListeners(onRankItemClickListener, data.getRole().getUser().getUserId());
    }

    @Override
    public int getItemCount() {
        return null == this.rankSortedList ? 0 : rankSortedList.size();
    }

    public static class RankViewHolder extends BaseRvViewHolder<Rank> {
        private GTextView rank;
        private ImageView avatar;
        private ImageView rankIv;
        private GTextView name;
        private GTextView score;
        private GTextView addFriend;
        private GTextView cancelOperation;
        private GTextView lookInfo;
        private LinearLayout operationContainer;
        private LinearLayout rankContainer;

        private Handler handler;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            handler = new Handler();
        }

        @Override
        protected void instantiateObject() {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void fillData(Rank data) {
            if (null != data) {
                String accountID = data.getRole().getUser().getUserId();
                score.setText("积分：" + data.getRole().getScore());
                int rankNum = data.getRankNum();
                if (rankNum == 1) {
                    rankIv.setVisibility(View.VISIBLE);
                    rankIv.setImageResource(R.drawable.jiang_bei_1);
                    avatar.setBackgroundResource(R.drawable.bg_rank_avatar_1);

                } else if (rankNum == 2) {
                    rankIv.setVisibility(View.VISIBLE);
                    rankIv.setImageResource(R.drawable.jiang_bei_2);
                    avatar.setBackgroundResource(R.drawable.bg_rank_avatar_2);

                } else if (rankNum == 3) {
                    rankIv.setVisibility(View.VISIBLE);
                    rankIv.setImageResource(R.drawable.jiang_bei_3);
                    avatar.setBackgroundResource(R.drawable.bg_rank_avatar_3);

                } else if (rankNum > 3) {
                    rankIv.setVisibility(View.GONE);
                    rank.setVisibility(View.VISIBLE);
                    rank.setText(String.valueOf(rankNum));
                    avatar.setBackgroundResource(R.drawable.bg_rank_avatar_other);

                }

                JMessageClient.getUserInfo(accountID, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0) {
                            handler.post(() -> Glide.with(getContext())
                                    .load(userInfo.getAvatarFile())
                                    .into(avatar));

                        }
                    }
                });
                if (accountID.equals(UserDataCache.readAccount(Constant.ACCOUNT_ID))) {
                    name.setText("我");

                } else {
                    name.setText(data.getRole().getName());

                }
            }
        }

        @Override
        protected void findViews() {
            rank = findGTextView(R.id.item_rank_num);
            avatar = findImageView(R.id.item_rank_avatar);
            name = findGTextView(R.id.item_rank_name);
            score = findGTextView(R.id.item_rank_score);
            rankIv = findImageView(R.id.item_rank_num_iv);
            operationContainer = findLinearLayout(R.id.item_rank_container_operation);
            rankContainer = findLinearLayout(R.id.item_rank_container);
            addFriend = findGTextView(R.id.item_rank_add_friend);
            lookInfo = findGTextView(R.id.item_rank_look_info);
            cancelOperation = findGTextView(R.id.item_rank_cancel);

        }

        public void setListeners(OnRankItemClickListener onRankItemClickListener, String accountID) {

            rankContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationUtil.switchViewsTraslate(getContext(), operationContainer, rankContainer);

                }
            });
            cancelOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationUtil.switchViewsTraslate(getContext(), rankContainer, operationContainer);

                }
            });
            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onRankItemClickListener) {
                        onRankItemClickListener.onAddFriend(accountID);

                    }
                }
            });
            lookInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onRankItemClickListener) {
                        onRankItemClickListener.onLookInfo(accountID);

                    }
                }
            });

        }


        public interface OnRankItemClickListener {
            void onAddFriend(String accountID);

            void onLookInfo(String accountID);

        }
    }
}
