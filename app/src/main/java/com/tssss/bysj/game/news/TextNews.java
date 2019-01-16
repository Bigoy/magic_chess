package com.tssss.bysj.game.news;

public class TextNews extends News {
    private int mUserHead;
    private String mUserName, mUserNews;

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

    public String getUserNews() {
        return mUserNews;
    }

    public void setUserNews(String mUserNews) {
        this.mUserNews = mUserNews;
    }

    @Override
    public int getNewsStyle() {
        return News.STYLE_TEXT;
    }
}
