package com.tssss.bysj.game.rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private List<Rank> roleList;
    private Context context;


    public RankAdapter(Context context, List<Rank> roleList) {
        this.context = context;
        this.roleList = roleList;
    }


    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rank, parent, false);
        return new RankViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        // 1、2、3名不用显示
        if (position == 0 || position == 1 || position == 2) {
            Logger.log("放弃显示" + position);

        } else {
            holder.fillData(roleList.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return null == this.roleList ? 0 : roleList.size();
    }

    public static class RankViewHolder extends BaseRvViewHolder<Rank> {
        private GTextView rank;
        private ImageView avatar;
        private GTextView name;
        private GTextView score;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {

        }

        @Override
        public void fillData(Rank data) {
            if (null != data) {
                rank.setText(data.getRankNum());
                Glide.with(getContext())
                        .load(data.getRole().getAvatarFile())
                        .into(avatar);
                name.setText(data.getRole().getName());
                score.setText("积分：" + data.getRole().getScore());

            }
        }

        @Override
        protected void findViews() {
            rank = findGTextView(R.id.item_rank_num);
            avatar = findImageView(R.id.item_rank_avatar);
            name = findGTextView(R.id.item_rank_name);
            score = findGTextView(R.id.item_rank_score);

        }
    }
}
