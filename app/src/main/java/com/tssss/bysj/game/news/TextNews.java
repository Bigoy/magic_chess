package com.tssss.bysj.game.news;

public class TextNews extends News {
    private String content;
    private String from;
    private String time;


    public TextNews() {
    }

    public TextNews(String content, String from, String time) {
        this.content = content;
        this.from = from;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public int getNewsStyle() {
        return News.STYLE_TEXT;
    }
}
