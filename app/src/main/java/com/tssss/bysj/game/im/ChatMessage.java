package com.tssss.bysj.game.im;

import java.io.File;

public class ChatMessage {
    public static final int MESSAGE_ME = 1;
    public static final int MESSAGE_NOT_ME = 2;

    private int messageFrom;

    private String userAvatar;

    private String message;

    private String time;

    private File avatarFile;

    private String fromAccountID;

    public String getFromAccountID() {
        return fromAccountID;
    }

    public void setFromAccountID(String fromAccountID) {
        this.fromAccountID = fromAccountID;
    }



    public File getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
    }



    public ChatMessage() {

    }

    public ChatMessage(int messageFrom,
                       String userAvatar,
                       String message,
                       String time) {

        this.messageFrom = messageFrom;
        this.userAvatar = userAvatar;
        this.message = message;
        this.time = time;

    }

    public ChatMessage(int messageFrom,
                       File avatarFile,
                       String message,
                       String time) {

        this.messageFrom = messageFrom;
        this.avatarFile = avatarFile;
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
