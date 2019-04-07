package com.tssss.bysj.game.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tssss.bysj.R;

import androidx.recyclerview.widget.RecyclerView;

public class PictureNewsHolder extends RecyclerView.ViewHolder {
    private TextView mUserNameTv;
    private ImageView mUserHeadIv, mUserNewsIv;

    private PictureNews mNews;

    public PictureNewsHolder(View itemView) {
        super(itemView);

        findViews();
    }

    private void findViews() {
        mUserNameTv = itemView.findViewById(R.id.item_news_p_name_tv);
        mUserHeadIv = itemView.findViewById(R.id.item_news_p_head_iv);
        mUserNewsIv = itemView.findViewById(R.id.item_news_p_news_iv);
    }

    public void setViews(News news) {
        this.mNews = (PictureNews) news;

        mUserNameTv.setText(mNews.getUserName());
        mUserHeadIv.setImageResource(mNews.getUserHead());
        mUserNewsIv.setImageBitmap(mNews.getUserNews());
    }
}
