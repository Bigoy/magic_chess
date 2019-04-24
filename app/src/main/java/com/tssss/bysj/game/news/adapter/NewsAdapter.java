package com.tssss.bysj.game.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tssss.bysj.R;
import com.tssss.bysj.game.news.News;
import com.tssss.bysj.game.news.PictureNews;
import com.tssss.bysj.game.news.PictureNewsHolder;
import com.tssss.bysj.game.news.TextNews;
import com.tssss.bysj.game.news.TextNewsHolder;
import com.tssss.bysj.game.news.TextPicNews;
import com.tssss.bysj.game.news.TextPicNewsHolder;
import com.tssss.bysj.other.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter {
    private List<News> mNewsList;
    private Context mContext;

    public NewsAdapter(Context context, List<News> news) {
        this.mContext = context;
        this.mNewsList = news;
    }

    /**
     * @param i viewType
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Logger.log(i);
        if (i == News.STYLE_TEXT) {
            return new TextNewsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_text,
                    viewGroup, false));

        } else if (i == News.STYLE_PICTURE) {
            return new PictureNewsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_pic,
                    viewGroup, false));

        } else if (i == News.STYLE_TEXT_PICTURE) {
            return new TextPicNewsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_text_pic,
                    viewGroup, false));

        }
        return null;
    }

    /**
     * @param i position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TextNewsHolder) {
            ((TextNewsHolder) viewHolder).fillData((TextNews) mNewsList.get(i));

        } else if (viewHolder instanceof PictureNewsHolder) {
            ((PictureNewsHolder) viewHolder).fillData((PictureNews) mNewsList.get(i));

        } else if (viewHolder instanceof TextPicNewsHolder) {
            ((TextPicNewsHolder) viewHolder).fillData((TextPicNews) mNewsList.get(i));

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mNewsList.get(position).getNewsStyle();
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
