package com.tssss.bysj.http;

import okhttp3.Request;

public class HttpRequestFactory {
    public static Request okhttpGetRequest(String url) {
        return new Request.Builder()
                .get()
                .url(url)
                .build();
    }
}
