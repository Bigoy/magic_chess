package com.tssss.bysj.game.news;

import java.util.List;

public class PictureNews extends News {
    private String from;
    private String time;
    /**
     * 图片以字符串的形式传输
     */
    private List<String> pictures;

    public PictureNews() {


    }

    public PictureNews(String from, String time, List<String> pictures) {
        this.from = from;
        this.time = time;
        this.pictures = pictures;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public int getNewsStyle() {
        return News.STYLE_PICTURE;
    }
}
