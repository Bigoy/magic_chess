package com.tssss.bysj.game.core.other;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 游戏角色
 */
public class GameRole {

    private User user;
    private String avatarStr;
    private File avatarFile;
    private String name;
    private String sex;
    private String signature;
    private String level;
    private int exp;
    private int score;
    private String chessmanCamp;
    private String[] levels = new String[]{
            Constant.ROLE_SX_I,
            Constant.ROLE_SX_II,
            Constant.ROLE_SX_III,
            Constant.ROLE_ZSQS_I,
            Constant.ROLE_ZSQS_II,
            Constant.ROLE_ZSQS_III,
            Constant.ROLE_LSJ_I,
            Constant.ROLE_LSJ_II,
            Constant.ROLE_LSJ_III,
            Constant.ROLE_SQDL_I,
            Constant.ROLE_SQDL_II,
            Constant.ROLE_SQDL_III,
            Constant.ROLE_DS_I,
            Constant.ROLE_DS_II,
            Constant.ROLE_DS_III,
            Constant.ROLE_DJQS_I,
            Constant.ROLE_DJQS_II,
            Constant.ROLE_DJQS_III
    };


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


    public File getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void upgrade(int exp, int score, UpgradeRoleCallBack callBack) {
        this.score += score;
        int newExp = this.exp + exp;
        if (Constant.ROLE_SX_I.equals(level)) {
            if (newExp >= 50) {
                this.level = Constant.ROLE_SX_II;
                this.exp = newExp - 50;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_SX_II.equals(level)) {
            if (newExp >= 100) {
                this.level = Constant.ROLE_SX_III;
                this.exp = newExp - 100;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_SX_III.equals(level)) {
            if (newExp >= 150) {
                this.level = Constant.ROLE_ZSQS_I;
                this.exp = newExp - 150;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_ZSQS_I.equals(level)) {
            if (newExp >= 100) {
                this.level = Constant.ROLE_ZSQS_II;
                this.exp = newExp - 100;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_ZSQS_II.equals(level)) {
            if (newExp >= 200) {
                this.level = Constant.ROLE_ZSQS_III;
                this.exp = newExp - 200;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_ZSQS_III.equals(level)) {
            if (newExp >= 300) {
                this.level = Constant.ROLE_LSJ_I;
                this.exp = newExp - 300;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_LSJ_I.equals(level)) {
            if (newExp >= 120) {
                this.level = Constant.ROLE_LSJ_II;
                this.exp = newExp - 120;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_LSJ_II.equals(level)) {
            if (newExp >= 240) {
                this.level = Constant.ROLE_LSJ_III;
                this.exp = newExp - 240;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_LSJ_III.equals(level)) {
            if (newExp >= 360) {
                this.level = Constant.ROLE_SQDL_I;
                this.exp = newExp - 360;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_SQDL_I.equals(level)) {
            if (newExp >= 150) {
                this.level = Constant.ROLE_SQDL_II;
                this.exp = newExp - 150;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_SQDL_II.equals(level)) {
            if (newExp >= 300) {
                this.level = Constant.ROLE_SQDL_III;
                this.exp = newExp - 300;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_SQDL_III.equals(level)) {
            if (newExp >= 450) {
                this.level = Constant.ROLE_DS_I;
                this.exp = newExp - 450;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_DS_I.equals(level)) {
            if (newExp >= 200) {
                this.level = Constant.ROLE_DS_II;
                this.exp = newExp - 200;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_DS_II.equals(level)) {
            if (newExp >= 400) {
                this.level = Constant.ROLE_DS_III;
                this.exp = newExp - 400;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_DS_III.equals(level)) {
            if (newExp >= 600) {
                this.level = Constant.ROLE_DJQS_I;
                this.exp = newExp - 600;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_DJQS_I.equals(level)) {
            if (newExp >= 300) {
                this.level = Constant.ROLE_DJQS_II;
                this.exp = newExp - 300;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_DJQS_II.equals(level)) {
            if (newExp >= 300) {
                this.level = Constant.ROLE_DJQS_III;
                this.exp = newExp - 300;
            } else {
                this.exp = newExp;
            }
        } else if (Constant.ROLE_DJQS_III.equals(level)) {
            this.exp = newExp;
        }
        Map<String, String> myInfoMap = new HashMap<>();
        myInfoMap.put(Constant.ACCOUNT_ID, this.user.getUserId());
        myInfoMap.put(Constant.ACCOUNT_PASSWORD, this.user.getUserPassword());
        myInfoMap.put(Constant.ROLE_NICK_NAME, this.name);
        myInfoMap.put(Constant.ROLE_SEX, this.sex);
        myInfoMap.put(Constant.ROLE_SIGNATURE, this.signature);
        myInfoMap.put(Constant.ROLE_LEVEL, this.level);
        myInfoMap.put(Constant.ROLE_EXP, String.valueOf(this.exp));
        myInfoMap.put(Constant.ROLE_SCORE, String.valueOf(this.score));
        UserInfo userInfo = JMessageClient.getMyInfo();
        String userInfoJson = JSON.toJSONString(myInfoMap);
        userInfo.setSignature(userInfoJson);
        JMessageClient.updateMyInfo(UserInfo.Field.signature, userInfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (null != callBack) {
                    if (i == 0) {
                        callBack.onSuccess();
                    } else {
                        callBack.onFailure(s);
                    }
                }
            }
        });
    }

    public interface UpgradeRoleCallBack {
        void onSuccess();

        void onFailure(String errorMsg);
    }
}
