package com.tssss.bysj.game.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tssss.bysj.R;

import androidx.recyclerview.widget.RecyclerView;

public class TextPicNewsHolder extends RecyclerView.ViewHolder {
    private TextView mUserNameTv, mUserNewsTv;
    private ImageView mUserHeadIv, mUserNewsIv;

    private TextPicNews mNews;

    public TextPicNewsHolder(View itemView) {
        super(itemView);

        findViews();
    }

    private void findViews() {
        mUserNameTv = itemView.findViewById(R.id.item_news_tp_name_tv);
        mUserNewsTv = itemView.findViewById(R.id.item_news_tp_news_tv);
        mUserHeadIv = itemView.findViewById(R.id.item_news_tp_head_iv);
        mUserNewsIv = itemView.findViewById(R.id.item_news_tp_news_iv);
    }

    public void setViews(News news) {
        this.mNews = (TextPicNews) news;

        mUserNameTv.setText(mNews.getUserName());
        mUserNewsTv.setText(mNews.getUserNewsStr());
        mUserHeadIv.setImageResource(mNews.getUserHead());
        mUserNewsIv.setImageBitmap(mNews.getUserNewsBm());
    }
}
