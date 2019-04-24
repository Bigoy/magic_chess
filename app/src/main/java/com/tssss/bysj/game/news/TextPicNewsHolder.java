package com.tssss.bysj.game.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.game.news.adapter.NewsPictureAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TextPicNewsHolder extends BaseRvViewHolder<TextPicNews> {
    private TextView contentText;
    private TextView from;
    private TextView time;
    private RecyclerView contentPicture;

    public TextPicNewsHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void instantiateObject() {

    }

    @Override
    public void fillData(TextPicNews data) {
        if (null != data) {
            contentText.setText(data.getContentText());
            from.setText(data.getFrom());
            time.setText(data.getTime());
            contentPicture.setLayoutManager(new GridLayoutManager(getContent(), 3));
            contentPicture.setAdapter(new NewsPictureAdapter(getContent(), data.getContentPicture()));

        }
    }

    @Override
    protected void findViews() {
        contentText = findTextView(R.id.item_news_tp_content_t);
        from = findTextView(R.id.item_news_tp_from);
        time = findTextView(R.id.item_news_tp_time);
        contentPicture = findRecyclerView(R.id.item_news_tp_content_p);
    }

}
