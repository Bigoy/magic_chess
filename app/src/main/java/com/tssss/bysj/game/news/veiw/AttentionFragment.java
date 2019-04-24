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
import com.tssss.bysj.game.news.contract.INewsAttentionContract;
import com.tssss.bysj.game.news.presenter.AttentionNewsPresenter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.fragment_news_attention)
public class AttentionFragment extends BaseFragment implements INewsAttentionContract.IVew {
    private RecyclerView recyclerView;
    private GTextView loading;
    private ImageView nullNews;

    private AttentionNewsPresenter presenter;
    private Handler handler;

    @Override
    protected void afterBindView() {
        presenter = new AttentionNewsPresenter(this);
        handler = new Handler();
    }

    @Override
    protected void setEventListeners() {

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
    protected void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.news_attention_rv);
        loading = (GTextView) findViewById(R.id.new_attention_loading_tv);
        nullNews = (ImageView) findViewById(R.id.new_attention_null_iv);
    }

    @Override
    public void showNews(List<News> newsList) {
        if (null != newsList && newsList.size() > 0) {
            nullNews.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new NewsAdapter(getContext(), newsList));
        } else {
            loading.setVisibility(View.GONE);
            nullNews.setVisibility(View.VISIBLE);
        }
    }
}
