package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.adapter.NewsAdapter;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.game.news.News;
import com.tssss.bysj.interfaces.OnNewsListener;
import com.tssss.bysj.presenter.NewsPresenter;
import com.tssss.bysj.util.ToastUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsActivity extends BaseActivity implements OnNewsListener {
    private RecyclerView mNewsRv;
    private ImageView mNullNewsIv;

    private NewsPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.requestNewsData(this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new NewsPresenter();
        return mPresenter;
    }

    @Override
    protected void findViews() {
        mNewsRv = findViewById(R.id.news_rv);
        mNullNewsIv = findViewById(R.id.news_null_iv);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.news_title;
    }

    @Override
    protected int getRightIbStyle() {
        return R.drawable.ic_btn_add;
    }

    @Override
    protected void clickRight() {
        openActivity(DistributeNewsActivity.class);
    }

    @Override
    public void onComplete(List<News> newsList) {
        mNewsRv.setAdapter(new NewsAdapter(this, newsList));
    }

    @Override
    public void onFail() {
        mNullNewsIv.setVisibility(View.VISIBLE);
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onNullNews() {
        mNullNewsIv.setVisibility(View.VISIBLE);
    }
}
