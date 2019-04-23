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
     * 账户相关
     */
    public static final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
    public static final String ACCOUNT_FOUND = "ACCOUNT_FOUND";
    public static final String ACCOUNT_ACCESSIBLE_DENY = "ACCESSIBLE_DENY";
    public static final String ACCOUNT_ACCESSIBLE_GRANT = "ACCESSIBLE_GRANT";

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
     * 登录状态
     */
    public static final String LOGIN_STATE = "LOGIN_STATE";
    public static final String LOGIN_STATE_SUCCESS = "LOGIN_STATE_SUCCESS";
    public static final String LOGIN_STATE_FAILED = "LOGIN_STATE_FAILED";
}
