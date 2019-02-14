package com.tssss.http.okhttp;

import com.tssss.http.response.IResponse;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by tssss on 2019/2/14.
 * <p>
 * 基于 OkHttp 框架下的 Response
 */
public class OkHttpResponse implements IResponse {
    protected Response response;

    public OkHttpResponse(Response response) {
        this.response = response;
    }

    /**
     * 获取基于 OkHttp 框架执行的网络请求返回的 Response 中的字符串结果
     */
    @Override
    public String getBodyString() {
        try {
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
