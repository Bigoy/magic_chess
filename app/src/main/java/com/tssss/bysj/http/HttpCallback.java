package com.tssss.bysj.http;

public interface HttpCallback {
    void onSuccess(String result);

    void onFailure(String errorMsg);
}
