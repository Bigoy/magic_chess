package com.tssss.bysj.game.news;

import com.tssss.bysj.R;
import com.tssss.bysj.R2;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

@ViewInject(layoutId = R.layout.fragment_news_newest)
public class NewestFragment extends BaseFragment {
    @BindView(R2.id.news_newest)
    RecyclerView recyclerView;
    @Override
    protected void afterBindView() {

    }

    @Override
    protected void setEventListeners() {

    }
}
