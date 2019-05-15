package com.tssss.bysj.other.jmessage;

import cn.jpush.im.android.api.content.TextContent;

public class TextMessageContent {
    private TextContent textContent;

    private String from;

    public TextContent getTextContent() {
        return textContent;
    }

    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
