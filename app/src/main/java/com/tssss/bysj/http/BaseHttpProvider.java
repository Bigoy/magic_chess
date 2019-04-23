package com.tssss.bysj.http;

import com.tssss.bysj.exception.NetException;

import java.util.Map;

/**
 * 提供http请求服务
 */
public abstract class BaseHttpProvider {
    public abstract String requestGet(String url, Map<String, ?> param) throws NetException;

    public abstract void requestAsyncGet(String url, Map<String, ?> param, HttpCallback callback);

    public abstract void requestPost(String url, Map<String, ?> param, HttpCallback callback);
}
