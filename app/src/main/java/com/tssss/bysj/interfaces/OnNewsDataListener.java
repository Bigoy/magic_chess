package com.tssss.bysj.interfaces;

import com.tssss.bysj.game.news.News;

import java.util.List;

public interface OnNewsDataListener {
    void onLoadNewsDataComplete(List<News> newsList);

    void onFail();

    void onNullNews();
}
