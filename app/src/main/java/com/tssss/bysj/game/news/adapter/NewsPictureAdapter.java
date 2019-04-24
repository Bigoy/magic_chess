package com.tssss.bysj.game.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.util.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsPictureAdapter extends RecyclerView.Adapter<NewsPictureAdapter.NewsPictureViewHolder> {
    private Context context;
    private List<String> pictures;

    public NewsPictureAdapter(Context context, List<String> pictures) {
        this.context = context;
        this.pictures = pictures;

    }

    @NonNull
    @Override
    public NewsPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsPictureViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPictureViewHolder holder, int position) {
        holder.fillData(pictures.get(position));
    }

    @Override
    public int getItemCount() {
        return null == pictures ? 0 : pictures.size();

    }

    public static class NewsPictureViewHolder extends BaseRvViewHolder<String> {
        private ImageView picture;


        public NewsPictureViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {

        }

        @Override
        public void fillData(String data) {
            if (!StringUtil.isBlank(data)) {
                Glide.with(getContent())
                        .load(data)
                        .into(picture);

            }
        }

        @Override
        protected void findViews() {
            picture = findImageView(R.id.item_news_picture);
        }
    }
}
