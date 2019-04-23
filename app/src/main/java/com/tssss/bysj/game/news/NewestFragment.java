package com.tssss.bysj.game.news;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;

import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.fragment_news_newest)
public class NewestFragment extends BaseFragment {
    private RecyclerView recyclerView;
    @Override
    protected void afterBindView() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void findViews() {
        findViewById(R.id.news_newest);
    }
}
