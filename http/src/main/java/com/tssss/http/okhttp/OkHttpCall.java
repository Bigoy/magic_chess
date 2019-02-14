package com.tssss.http.okhttp;

import com.tssss.http.request.IRequest;
import com.tssss.http.request.call.ICall;
import com.tssss.http.response.IResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tssss on 2019/2/14.
 * <p>
 * 基于 OkHttp 框架实现本框架的 Call
 */
public class OkHttpCall implements ICall {
    private Call call;
    private IRequest request;

    public OkHttpCall(IRequest request, Call call) {
        this.request = request;
        this.call = call;
    }

    @Override
    public IResponse execute() {
        Response response = null;
        try {
            // OkHttp 实现真正的网络请求
            response = this.call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new OkHttpResponse(response);
    }

    @Override
    public IRequest getRequest() {
        return this.request;
    }
}
