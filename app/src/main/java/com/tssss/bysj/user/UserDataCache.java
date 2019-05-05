package com.tssss.bysj.user;

import android.util.Log;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.other.SharedPreferencesFactory;
import com.tssss.bysj.util.SharedPreferencesUtil;
import com.tssss.bysj.util.StringUtil;

/**
 * 用户数据缓存
 * <p>
 * 保存用户账户信息到本地
 */
public class UserDataCache {

    /**
     * 保存用户信息
     */
    public static void saveAccount(User user) {
        if (null == user) {
            Log.w("UserDataCache", "user == null");
        } else {
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_ID, user.getUserId());
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_PASSWORD, user.getUserPassword());
            Logger.log(readAccount(Constant.ACCOUNT_ID));
            Logger.log(readAccount(Constant.ACCOUNT_PASSWORD));
        }

    }

    /**
     * 读取用户信息
     * id
     * 密码
     */
    public static String readAccount(String key) {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getUserSharedPreferences(), key);
    }

    /**
     * 用户退出登录时清除账户缓存信息
     */
    public static void clearUserCache() {
        SharedPreferencesFactory.getUserSharedPreferences().edit().clear().apply();
    }

    public static void keepLastLoginTime(String time) {
        SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_LOGIN_TIME, time);
    }

    public static String readLastLoginTime() {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ACCOUNT_LOGIN_TIME);
    }

    public static void keepRole(GameRole gameRole) {
        if (null != gameRole) {
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_AVATAR, gameRole.getAvatarStr());
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_NICK_NAME, gameRole.getName());
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_SEX, gameRole.getSex());
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_SIGNATURE, gameRole.getSignature());
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_LEVEL, gameRole.getLevel());
            SharedPreferencesUtil.keepInt(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_EXP, gameRole.getRoleExperience());
        }
    }

    public static GameRole readRole() {
        String avatar = readString(Constant.ROLE_AVATAR);
        String name = readString(Constant.ROLE_NICK_NAME);
        String sex = readString(Constant.ROLE_SEX);
        String signature = readString(Constant.ROLE_SIGNATURE);
        String level = readString(Constant.ROLE_LEVEL);
        int exp = readInt(Constant.ROLE_EXP);
        GameRole gameRole = null;
        if (StringUtil.isBlank(avatar) || StringUtil.isBlank(name) || StringUtil.isBlank(sex) || StringUtil.isBlank(signature) || StringUtil.isBlank(level)) {
            return gameRole;
        } else {
            gameRole = new GameRole();
            gameRole.setUser(new User(readAccount(Constant.ACCOUNT_ID), readAccount(Constant.ACCOUNT_PASSWORD)));
            gameRole.setAvatarStr(avatar);
            gameRole.setName(name);
            gameRole.setSex(sex);
            gameRole.setSignature(signature);
            gameRole.setLevel(level);
            gameRole.setRoleExperience(exp);

        }
        return gameRole;
    }

    public static void keepString(String key, String value) {
        SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), key, value);
    }

    public static String readString(String key) {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getUserSharedPreferences(), key);
    }

    public static int readInt(String key) {
        return SharedPreferencesUtil.readInt(SharedPreferencesFactory.getUserSharedPreferences(), key);
    }
}
