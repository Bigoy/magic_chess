package com.tssss.bysj.http;

/**
 * 游戏需要用到的url
 */
public class HttpUrl {
    private static final String host = "http://www.magicchess.com";

    /**
     * 处理登录事件
     */
    public static final String URL_LOGIN = host + "/login";
    /**
     * 处理注册事件
     */
    public static final String URL_REGISTER = host + "/register";
    /**
     * 加载新闻动态
     */
    public static final String URL_NEWS = host + "/news";
    /**
     * 动态：推荐内容
     */
    public static final String URL_NEWS_RECOMMEND = host + "/news/recommend";
    /**
     * 动态：玩家关注的人的最新动态
     */
    public static final String URL_NEWS_ATTENTION = host + "/news/attention";
    /**
     * 动态：其它，与游戏无关的内容
     */
    public static final String URL_NEWS_OTHER = host + "/news/other";
    /**
     * 动态：最新内容
     */
    public static final String URL_NEWS_NEWEST = host + "/news/newest";
}
