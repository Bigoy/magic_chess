package com.tssss.bysj.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpClientFactory {
    /**
     * 返回okhttp客户端
     * 默认配置相关属性
     */
    public static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }
}
