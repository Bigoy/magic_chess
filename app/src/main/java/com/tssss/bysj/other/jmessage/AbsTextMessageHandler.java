package com.tssss.bysj.other.jmessage;

public abstract class AbsTextMessageHandler {
    protected TextMessageContent textMessageContent;

    public AbsTextMessageHandler(TextMessageContent textMessageContent) {
        this.textMessageContent = textMessageContent;
    }

    public abstract void handleTextMessage();
}
