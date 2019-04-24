package com.tssss.bysj.game.news;

import java.util.List;

public class TextPicNews extends News {
    private String contentText;
    private String from;
    private String time;
    private List<String> contentPicture;

    public TextPicNews() {

    }

    public TextPicNews(String contentText, String from, String time, List<String> contentPicture) {
        this.contentText = contentText;
        this.from = from;
        this.time = time;
        this.contentPicture = contentPicture;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
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

    public List<String> getContentPicture() {
        return contentPicture;
    }

    public void setContentPicture(List<String> contentPicture) {
        this.contentPicture = contentPicture;
    }

    @Override

    public int getNewsStyle() {
        return News.STYLE_TEXT_PICTURE;
    }
}
