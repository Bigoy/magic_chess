package com.tssss.bysj.game.news;

import android.view.View;
import android.widget.TextView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.game.news.adapter.NewsPictureAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PictureNewsHolder extends BaseRvViewHolder<PictureNews> {
    private TextView from;
    private TextView time;
    private RecyclerView content;

    public PictureNewsHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void instantiateObject() {

    }

    @Override
    public void fillData(PictureNews data) {
        if (null != data) {
            from.setText(data.getFrom());
            time.setText(data.getTime());
            content.setLayoutManager(new GridLayoutManager(getContext(), 3));
            content.setAdapter(new NewsPictureAdapter(getContext(), data.getPictures()));
        }
    }

    @Override
    protected void findViews() {
        from = findTextView(R.id.item_news_p_from);
        time = findTextView(R.id.item_news_p_time);
        content = findRecyclerView(R.id.item_news_p_content);
    }

}
