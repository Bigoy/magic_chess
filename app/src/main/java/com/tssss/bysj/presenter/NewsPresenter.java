package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.game.news.News;
import com.tssss.bysj.interfaces.OnNewsDataListener;
import com.tssss.bysj.interfaces.OnNewsListener;
import com.tssss.bysj.model.NewsModel;

import java.util.List;

public class NewsPresenter extends PresenterImp implements OnNewsDataListener {
    private OnNewsListener mListener;
    private NewsModel mModel;

    public void requestNewsData(OnNewsListener listener) {
        this.mListener = listener;

        mModel = new NewsModel();
        mModel.loadNewsData(this);
    }

    @Override
    public void onLoadNewsDataComplete(List<News> newsList) {
        mListener.onComplete(newsList);
    }

    @Override
    public void onFail() {
        mListener.onFail();
    }

    @Override
    public void onNullNews() {
        mListener.onNullNews();
    }
}
