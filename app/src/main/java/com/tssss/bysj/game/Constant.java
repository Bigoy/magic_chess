package com.tssss.bysj.game;

/*
常量类。
某些情况下，获取上下文的不方便导致获取string.xml的字符串内容不方便。
 */
public class Constant {
    // alert.
    public static String ALERT_GAMING_QUIT = "如果此时退出，你将被判决为输，确定退出吗?";

    // choice.
    public static String CHECK_NOT_TURN = "轮到对手走棋。";
    public static String CHECK_NOT_SELF = "只能选中自己的棋子。";
    public static String CHECK_NO_CHESSMAN = "这里没有棋子，请重新选择。";

    // function.
    public static String FUNC_NOT_SUPPORT = "该功能暂未上线，敬请期待。";

    // net
    public static String NET_GET_FAILED = "网络请求失败。";
    public static String NET_GET_SUCCEED = "网络请求成功。";
    public static int NET_POST_SUCCEED = 0;
    public static int NET_POST_FAILED = 1;

    // json key
    public static String JSON_KEY_USER_STATE = "userState";
    public static String JSON_KEY_GAME_ROLE = "gameRole";
    public static String JSON_KEY_ROLE_HEAD = "roleHead";
    public static String JSON_KEY_ROLE_NAME = "roleName";
    public static String JSON_KEY_ROLE_SEX = "roleSex";
    public static String JSON_KEY_ROLE_LEVEL = "roleLevel";
    public static String JSON_KEY_ROLE_EXPERIENCE = "roleExperience";

}

