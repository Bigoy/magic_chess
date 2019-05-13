package com.tssss.bysj.http;

public interface IHttpCallback {
    void onSuccess(String result);

    void onFailure(String errorMsg);
}
