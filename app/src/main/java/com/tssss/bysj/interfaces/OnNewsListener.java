package com.tssss.bysj.interfaces;

import com.tssss.bysj.game.news.News;

import java.util.List;

public interface OnNewsListener {
    void onComplete(List<News> newsList);

    void onFail();

    void onNullNews();
}
