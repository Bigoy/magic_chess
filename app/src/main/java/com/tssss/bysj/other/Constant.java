package com.tssss.bysj.other;

public class Constant {
    public static final boolean DEBUG = true;

    /**
     * 三方
     */
    public static final String JMESSAGE_APP_KEY = "e0ae5aeb8eda030f9e338f64";
    public static final String JMESSAGE_MESSAGE_KEY = "MAGIC_CHESS";

    /**
     * 网络相关
     */
    public static final int NET_CODE_PAGE_NOT_FOUND = 404;
    public static final int NET_CODE_OK = 200;
    public static final int NET_CODE_UNKNOWN = 915;
    public static final String NET_OFFLINE = "OFFLINE";

    public static final String BASE_URL = "www.magicchess.com";

    /**
     * json key
     */
    public static final String JSON_KEY_ACCOUNT_EXIST = "ACCOUNT_EXIST";
    public static final String JSON_KEY_ACCOUNT_ACCESSIBLE = "ACCOUNT_ACCESSIBLE";
    public static final String JSON_KEY_ACCOUNT = "ACCOUNT";
    public static final String JSON_KEY_ACCOUNT_PASSWORD = "ACCOUNT_PASSWORD";
    public static final String JSON_KEY_ROLE = "JSON_KEY_ROLE";

    // news
    public static final String JSON_KEY_NEWS = "JSON_KEY_NEWS";
    public static final String JSON_KEY_NEWS_TYPE = "JSON_KEY_NEWS_TYPE";
    public static final String JSON_KEY_NEWS_TEXT_CONTENT = "NEWS_TEXT_CONTENT";
    public static final String JSON_KEY_NEWS_TEXT_FROM = "NEWS_TEXT_FROM";
    public static final String JSON_KEY_NEWS_TEXT_TIME = "NEWS_TEXT_TIME";
    public static final String JSON_KEY_NEWS_PICTURE_CONTENT = "NEWS_PICTURE_CONTENT";
    public static final String JSON_KEY_NEWS_PICTURE_FROM = "NEWS_PICTURE_FROM";
    public static final String JSON_KEY_NEWS_PICTURE_TIME = "NEWS_PICTURE_TIME";
    public static final String JSON_KEY_NEWS_TP_TIME = "NEWS_TP_TIME";
    public static final String JSON_KEY_NEWS_TP_CONTENT_TEXT = "NEWS_TP_CONTENT_TEXT";
    public static final String JSON_KEY_NEWS_TP_CONTENT_PICTURE = "NEWS_TP_CONTENT_PICTURE";
    public static final String JSON_KEY_NEWS_TP_FROM = "NEWS_TP_FROM";

    // friends
    public static final String JSON_KEY_FRIEND_LIST = "FRIEND_LIST";
    public static final String JSON_KEY_FRIEND_AVATAR = "FRIEND_AVATAR";
    public static final String JSON_KEY_FRIEND_NAME = "FRIEND_NAME";
    public static final String JSON_KEY_FRIEND_SEX = "FRIEND_SEX";
    public static final String JSON_KEY_FRIEND_SIGNATURE = "FRIEND_SIGNATURE";
    public static final String JSON_KEY_FRIEND_LEVEL = "FRIEND_LEVEL";


    // recommend role
    public static final String JSON_KEY_FRIEND_ADD_RECOMMEND = "FRIEND_ADD_RECOMMEND_LIST";
    public static final String JSON_KEY_FRIEND_ADD_ID = "FRIEND_ADD_RECOMMEND_ID";
    public static final String JSON_KEY_FRIEND_ADD_AVATAR = "FRIEND_ADD_AVATAR";
    public static final String JSON_KEY_FRIEND_ADD_NAME = "FRIEND_ADD_NAME";
    public static final String JSON_KEY_FRIEND_ADD_SEX = "FRIEND_ADD_SEX";
    public static final String JSON_KEY_FRIEND_ADD_SIGNATURE = "FRIEND_ADD_SIGNATURE";
    public static final String JSON_KEY_FRIEND_ADD_LEVEL = "FRIEND_ADD_LEVEL";


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
    public static final String ACCOUNT_LOGIN_TIME = "ACCOUNT_LOGIN_TIME";

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

    //   实习生、正式棋手、老司机、
    //    神奇大佬、大师、顶级棋手
    public static final String ROLE_LEVEL = "ROLE_LEVEL";
    public static final String ROLE_SX = "实习生";
    public static final String ROLE_ZSQS = "正式棋手";
    public static final String ROLE_LSJ = "老司机";
    public static final String ROLE_SQDL = "神奇大佬";
    public static final String ROLE_DS = "大师";
    public static final String ROLE_DJQS = "顶级棋手";

    public static final String ROLE_EXP = "ROLE_EXP";

    /**
     * 用户偏好设置
     */
    public static final String SP_FUNCTION_NEWS_STATE = "SP_FUNCTION_NEWS_STATE";
    public static final String SP_FUNCTION_NEWS_STATE_CLOSED = "SP_FUNCTION_NEWS_STATE_CLOSED";
    public static final String SP_FUNCTION_NEWS_STATE_USING = "SP_FUNCTION_NEWS_STATE_USING";

    public static final String JMESSAGE_KEY = "J_MESSAGE_KEY";
}
