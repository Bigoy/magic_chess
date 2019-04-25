package com.tssss.bysj.game.im;

public class ChatMessage {
    public static final int MESSAGE_SEND = 1;
    public static final int MESSAGE_RECEIVER = 2;

    private int messageFrom;

    private String userAvatar;

    private String message;

    private String time;

    public ChatMessage() {


    }

    public ChatMessage(int messageFrom, String userAvatar, String message, String time) {
        this.messageFrom = messageFrom;
        this.userAvatar = userAvatar;
        this.message = message;
        this.time = time;

    }

    public int getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(int messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
