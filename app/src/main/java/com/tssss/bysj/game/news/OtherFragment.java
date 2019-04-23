package com.tssss.bysj.game.news;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;

import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.fragment_news_other)
public class OtherFragment extends BaseFragment {
    private RecyclerView recyclerView;
    @Override
    protected void afterBindView() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void findViews() {
        recyclerView =  (RecyclerView)findViewById(R.id.news_other);
    }
}
