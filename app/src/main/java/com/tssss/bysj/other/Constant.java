package com.tssss.bysj.other;

public class Constant {
    /**
     * 网络相关
     */
    public static final int NET_CODE_PAGE_NOT_FOUND = 404;
    public static final int NET_CODE_OK = 200;
    public static final int NET_CODE_UNKNOWN = 915;
    public static final String NET_OFFLINE = "OFFLINE";

    public static final String BASE_URL = "www.magicchess.com";

    /**
     * 登录操作相关的 json key
     */
    public static final String JSON_KEY_ACCOUNT_EXIST = "ACCOUNT_EXIST";
    public static final String JSON_KEY_ACCOUNT_ACCESSIBLE = "ACCOUNT_ACCESSIBLE";
    public static final String JSON_KEY_ACCOUNT = "ACCOUNT";
    public static final String JSON_KEY_ACCOUNT_PASSWORD = "ACCOUNT_PASSWORD";

    /**
     * 数据持久化 KEY
     */
    public static final String SETTING_NOTIFY_VOICE = "SETTING_NOTIFY_VOICE";
    public static final String SETTING_NOTIFY_VERBOSE = "SETTING_NOTIFY_VERBOSE";

    /**
     * 账号登录状态
     * 已经登录
     * 未登录，退出了登录
     */
    public static final String ACCOUNT_STATE = "ACCOUNT_STATE";
    public static final String ACCOUNT_STATE_LOGIN = "ACCOUNT_STATE_LOGIN";
    public static final String ACCOUNT_STATE_LOGOUT = "ACCOUNT_STATE_LOGOUT";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    public static final String ACCOUNT_PASSWORD = "ACCOUNT_PASSWORD";

    /**
     * 登录
     * 通过不同状态与用户交互
     */
    public static final String LOGIN_STATE = "LOGIN_STATE";

    public static final String LOGIN_STATE_SUCCESS = "LOGIN_STATE_SUCCESS";
    public static final String LOGIN_STATE_FAILED = "LOGIN_STATE_FAILED";
    public static final String LOGIN_STATE_NOT_REGISTER = "LOGIN_STATE_NOT_REGISTER";
    public static final String LOGIN_STATE_ERROR_PASSWORD = "LOGIN_STATE_ERROR_PASSWORD";

    /**
     * 注册
     */
    public static final String REGISTER_RESULT = "REGISTER_RESULT";

    public static final String REGISTER_RESULT_SUCCESS = "REGISTER_RESULT_SUCCESS";
    public static final String REGISTER_RESULT_FAILED = "REGISTER_RESULT_FAILED";

    /**
     * 游戏角色
     */
    public static final String ROLE_AVATAR = "ROLE_AVATAR";

    public static final String ROLE_SEX = "ROLE_SEX";
    public static final String ROLE_SEX_MAN = "MAN";
    public static final String ROLE_SEX_WOMAN = "WOMAN";
    public static final String ROLE_SEX_SECRET = "SECRET";

    public static final String ROLE_NICK_NAME = "ROLE_NICK_NAME";
    public static final String ROLE_SIGNATURE = "ROLE_SIGNATURE";
}
