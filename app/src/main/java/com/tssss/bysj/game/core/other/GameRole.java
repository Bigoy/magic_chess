package com.tssss.bysj.game.core.other;

import com.tssss.bysj.user.User;

import java.io.File;

/**
 * 游戏角色
 */
public class GameRole {

    /**
     * 游戏角色对应的账户
     */
    private User user;
    private String avatarStr;
    private File avatarFile;
    private String name;
    private String sex;
    private String signature;
    private String level;
    private int exp;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getChessmanCamp() {
        return chessmanCamp;
    }

    public void setChessmanCamp(String chessmanCamp) {
        this.chessmanCamp = chessmanCamp;
    }

    private String chessmanCamp;

    public File getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
    }

    public int getRoleExperience() {
        return exp;
    }

    public void setRoleExperience(int exp) {
        this.exp = exp;
    }

    public GameRole() {
    }

    public GameRole(User user,
                    String avatar,
                    String name,
                    String sex,
                    String signature,
                    String level,
                    int exp,
                    int score) {
        this.user = user;
        this.avatarStr = avatar;
        this.name = name;
        this.sex = sex;
        this.signature = signature;
        this.level = level;
        this.exp = exp;
        this.score = score;

    }

    public GameRole(User user,
                    File avatar,
                    String name,
                    String sex,
                    String signature,
                    String level,
                    int exp,
                    int score) {
        this.user = user;
        this.avatarFile = avatar;
        this.name = name;
        this.sex = sex;
        this.signature = signature;
        this.level = level;
        this.exp = exp;
        this.score = score;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvatarStr() {
        return avatarStr;
    }

    public void setAvatarStr(String avatarStr) {
        this.avatarStr = avatarStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
