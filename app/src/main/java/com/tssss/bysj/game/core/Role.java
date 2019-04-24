package com.tssss.bysj.game.core;

import com.tssss.bysj.user.User;

/**
 * 游戏角色
 */
public class Role {

    /**
     * 游戏角色对应的账户
     */
    private User user;
    private String avatar;
    private String name;
    private String sex;
    private String signature;
    private String level;


    public Role() {
    }

    public Role(String avatar,
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

    public Role(User user,
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
