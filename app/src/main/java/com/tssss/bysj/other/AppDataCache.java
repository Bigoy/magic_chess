package com.tssss.bysj.other;

import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.SharedPreferencesUtil;
import com.tssss.bysj.util.StringUtil;

public class AppDataCache {
    public static void keepAccountState(String state) {
        SharedPreferencesUtil.keepString(SharedPreferencesFactory.getAppSharedPreferences(), Constant.ACCOUNT_STATE, state);
    }

    public static String readAccountState() {
        return SharedPreferencesUtil.readString(SharedPreferencesFactory.getAppSharedPreferences(), Constant.ACCOUNT_STATE);
    }
    public static void keepRole(GameRole gameRole) {
        if (null != gameRole) {
            SharedPreferencesUtil.keepString(SharedPreferencesFactory.getUserSharedPreferences(), Constant.ROLE_AVATAR, gameRole.getAvatar());
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
        if (StringUtil.isBlank(name)) {
            return gameRole;
        } else {
            gameRole = new GameRole();
            gameRole.setUser(new User(UserDataCache.readAccount(Constant.ACCOUNT_ID), UserDataCache.readAccount(Constant.ACCOUNT_PASSWORD)));
            gameRole.setAvatar(avatar);
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
