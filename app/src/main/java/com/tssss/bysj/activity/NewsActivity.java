package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.adapter.NewsAdapter;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.game.news.News;
import com.tssss.bysj.interfaces.OnNewsListener;
import com.tssss.bysj.presenter.NewsPresenter;
import com.tssss.bysj.util.ToastUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ContentView(R.layout.activity_news)
public class NewsActivity extends BaseActivity implements View.OnClickListener, OnNewsListener {
    private ImageButton mBackIb, mAddNewsIb;
    private ImageView mNullIv;
    private RecyclerView mNewsRv;

    private NewsPresenter mPresenter;

    @Override
    protected void findViews() {
        mBackIb = findViewById(R.id.news_back_ib);
        mAddNewsIb = findViewById(R.id.news_add_ib);
        mNullIv = findViewById(R.id.news_null_iv);
        mNewsRv = findViewById(R.id.news_rv);
    }

    @Override
    protected void setEventListeners() {
        mBackIb.setOnClickListener(this);
        mAddNewsIb.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.requestNewsData(this, this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new NewsPresenter();
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_back_ib:
                finish();
                break;
            case R.id.news_add_ib:
                addNews();
                break;
        }
    }


    @Override
    public void onComplete(List<News> newsList) {
        NewsAdapter adapter = new NewsAdapter(this, newsList);

        mNullIv.setVisibility(View.GONE);

        mNewsRv.setVisibility(View.VISIBLE);
        mNewsRv.setLayoutManager(new LinearLayoutManager(this));
        mNewsRv.setAdapter(adapter);
    }

    @Override
    public void onFail() {
        ToastUtil.showToast(this, "load news error", ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onNullNews() {
        mNullIv.setVisibility(View.VISIBLE);
    }

    private void addNews() {
    }
}
