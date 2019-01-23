package com.tssss.bysj.model;

import com.tssss.bysj.game.news.News;
import com.tssss.bysj.interfaces.OnNewsDataListener;
import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;

import java.util.ArrayList;
import java.util.List;

public class NewsModel implements IDataListener {
    private OnNewsDataListener mListener;

    public void loadNewsData(OnNewsDataListener listener) {
        mListener = listener;

        TaoHttpClient client = new TaoHttpClient(
                HttpConstant.BASE_URL,
                null,
                new JsonHttpRequest(),
                new JsonHttpResponse(this));
        client.request();
    }

    @Override
    public void onSuccess(Object data) {
        List<News> news = new ArrayList();
        mListener.onLoadNewsDataComplete(news);
    }

    @Override
    public void onFailure() {
        mListener.onFail();
    }
}
