package com.tssss.bysj.game.news;

import android.graphics.Bitmap;

public class PictureNews extends News {
    private int mUserHead;
    private String mUserName;
    private Bitmap mUserNews;

    public int getUserHead() {
        return mUserHead;
    }

    public void setUserHead(int mUserHead) {
        this.mUserHead = mUserHead;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public Bitmap getUserNews() {
        return mUserNews;
    }

    public void setUserNews(Bitmap mUserNews) {
        this.mUserNews = mUserNews;
    }

    @Override
    public int getNewsStyle() {
        return News.STYLE_PICTURE;
    }
}
