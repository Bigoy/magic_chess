package com.tssss.bysj.login;

public interface OnLoginDataListener {
    void onLoadDataCompleted(com.alibaba.fastjson.JSONObject userJson);

    void onUserNotExit();

    void onUserPasswordError();

    void onFailure();
}
