package com.tssss.bysj.game.im;

public class ChatMessage {
    public static final int MESSAGE_SEND = 1;
    public static final int MESSAGE_RECEIVER = 2;

    private int messageFrom;

    private int userAvatar;

    private String message;

    private String time;

    public int getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(int messageFrom) {
        this.messageFrom = messageFrom;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
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
