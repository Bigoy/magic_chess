package com.tssss.bysj.model;

import android.content.Context;

import com.tssss.bysj.interfaces.OnNewsDataListener;

public class NewsModel {
    public void loadNewsData(Context context, OnNewsDataListener listener) {
        listener.onNullNews();
    }
}
