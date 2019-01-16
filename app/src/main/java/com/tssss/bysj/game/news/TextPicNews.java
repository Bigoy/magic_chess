package com.tssss.bysj.game.news;

import android.graphics.Bitmap;

public class TextPicNews extends News {
    private int mUserHead;
    private String mUserName, mUserNewsStr;
    private Bitmap mUserNewsBm;

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

    public String getUserNewsStr() {
        return mUserNewsStr;
    }

    public void setUserNewsStr(String mUserNewsStr) {
        this.mUserNewsStr = mUserNewsStr;
    }

    public Bitmap getUserNewsBm() {
        return mUserNewsBm;
    }

    public void setUserNewsBm(Bitmap mUserNewsBm) {
        this.mUserNewsBm = mUserNewsBm;
    }

    @Override
    public int getNewsStyle() {
        return News.STYLE_TEXT_PICTURE;
    }
}
