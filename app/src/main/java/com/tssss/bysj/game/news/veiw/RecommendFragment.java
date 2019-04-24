package com.tssss.bysj.game.news.veiw;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.news.News;
import com.tssss.bysj.game.news.adapter.NewsAdapter;
import com.tssss.bysj.game.news.contract.INewsRecommendContract;
import com.tssss.bysj.game.news.presenter.RecommendNewsPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.fragment_news_recommend)
public class RecommendFragment extends BaseFragment implements INewsRecommendContract.IVew {
    private RecyclerView recyclerView;
    private ImageView nullNewsIV;
    private GTextView loadingNewsTv;

    private RecommendNewsPresenter presenter;
    private Handler handler;

    @Override
    protected void afterBindView() {
        presenter = new RecommendNewsPresenter(this);
        handler = new Handler();
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadNews();
            }
        }, 300);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.news_recommend);
        nullNewsIV = (ImageView) findViewById(R.id.new_recommend_null_iv);
        loadingNewsTv = (GTextView) findViewById(R.id.new_recommend_loading_tv);
    }

    @Override
    public void showNews(List<News> newsList) {
        if (null != newsList && newsList.size() > 0) {
            nullNewsIV.setVisibility(View.GONE);
            loadingNewsTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new NewsAdapter(getContext(), newsList));
        } else {
            loadingNewsTv.setVisibility(View.GONE);
            nullNewsIV.setVisibility(View.VISIBLE);
        }
    }
}
