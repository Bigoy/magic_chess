package com.tssss.bysj.game.core;

import com.tssss.bysj.other.Constant;
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
    private String avatar;
    private File avatarFile;
    private String name;
    private String sex;
    private String signature;
    private String level = Constant.ROLE_SX;
    private int exp = 0;


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

    public GameRole(String avatar,
                    String name,
                    String sex,
                    String signature,
                    String level) {
        this.avatar = avatar;
        this.name = name;
        this.sex = sex;
        this.signature = signature;
        this.level = level;
    }

    public GameRole(User user,
                    String avatar,
                    String name,
                    String sex,
                    String signature,
                    String level) {
        this.user = user;
        this.avatar = avatar;
        this.name = name;
        this.sex = sex;
        this.signature = signature;
        this.level = level;

    }

    public GameRole(User user,
                    String avatar,
                    String name,
                    String sex,
                    String signature,
                    String level,
                    int exp) {
        this.user = user;
        this.avatar = avatar;
        this.name = name;
        this.sex = sex;
        this.signature = signature;
        this.level = level;
        this.exp = exp;

    }

    public GameRole(User user,
                    File avatar,
                    String name,
                    String sex,
                    String signature,
                    String level,
                    int exp) {
        this.user = user;
        this.avatarFile = avatar;
        this.name = name;
        this.sex = sex;
        this.signature = signature;
        this.level = level;
        this.exp = exp;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
