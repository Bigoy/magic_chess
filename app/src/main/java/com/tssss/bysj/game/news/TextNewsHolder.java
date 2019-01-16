package com.tssss.bysj.game.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tssss.bysj.R;

import androidx.recyclerview.widget.RecyclerView;

public class TextNewsHolder extends RecyclerView.ViewHolder {
    private TextView mUserNameTv, mUserNewsTv;
    private ImageView mUserHeadIv;

    private TextNews mNews;

    public TextNewsHolder(View itemView) {
        super(itemView);

        findViews();
    }

    private void findViews() {
        mUserNameTv = itemView.findViewById(R.id.item_news_t_name_tv);
        mUserNewsTv = itemView.findViewById(R.id.item_news_t_news_tv);
        mUserHeadIv = itemView.findViewById(R.id.item_news_t_head_iv);
    }

    public void setViews(News news) {
        this.mNews = (TextNews) news;

        mUserHeadIv.setImageResource(mNews.getUserHead());
        mUserNameTv.setText(mNews.getUserName());
        mUserNewsTv.setText(mNews.getUserNews());
    }
}
