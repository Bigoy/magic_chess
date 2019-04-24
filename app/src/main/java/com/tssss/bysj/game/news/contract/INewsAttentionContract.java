package com.tssss.bysj.game.news.contract;

import com.tssss.bysj.game.news.News;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface INewsAttentionContract {
    interface IVew extends IMvpView {
        void showNews(List<News> newsList);
    }

    INewsAttentionContract.IVew emptyView = new INewsAttentionContract.IVew() {
        @Override
        public void showNews(List<News> newsList) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };


    interface IPresenter extends ILifeCircle {
        void loadNews();

    }

}
