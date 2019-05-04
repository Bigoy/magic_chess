package com.tssss.bysj.http;

import com.tssss.bysj.other.Constant;
import com.tssss.bysj.security.Token;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpRequestFactory {
    public static Request okhttpGetRequest(String url) {
        return new Request.Builder()
                .get()
                .addHeader("Authorization", "Basic " + Token.token())
                .url(url)
                .build();
    }

    public static Request okhttpPostStringRequest(String url, String content) {
        return new Request.Builder()
                .post(RequestBody.create(MediaType.parse(com.tssss.bysj.http.MediaType.MEDIA_TYPE_JSON), content))
                .url(url)
                .build();

    }
}
