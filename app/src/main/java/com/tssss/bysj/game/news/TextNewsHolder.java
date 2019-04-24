package com.tssss.bysj.game.news;

import android.view.View;
import android.widget.TextView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;

import androidx.annotation.NonNull;

public class TextNewsHolder extends BaseRvViewHolder<TextNews> {
    private TextView content;
    private TextView from;
    private TextView time;

    public TextNewsHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void instantiateObject() {

    }

    @Override
    public void fillData(TextNews data) {
        if (null != data) {
            content.setText(data.getContent());
            from.setText(data.getFrom());
            time.setText(data.getTime());
        }
    }

    @Override
    protected void findViews() {
        content = findTextView(R.id.item_news_t_content);
        from = findTextView(R.id.item_news_t_from);
        time = findTextView(R.id.item_news_t_time);
    }

}
