package com.tssss.bysj.login;

import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.JsonPostHttpRequest;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.User;

class LoginModel implements IDataListener {
    private OnLoginDataListener mListener;

    /**
     * Create http task, request login state
     *
     * @param userId   phone number
     * @param password password
     * @param listener callback listener
     */
    void loadUserData(long userId, String password, OnLoginDataListener listener) {
        this.mListener = listener;

        User user = new User(userId, password);

        TaoHttpClient taoHttpClient = new TaoHttpClient(HttpConstant.BASE_URL, user,
                new JsonPostHttpRequest(), new JsonHttpResponse<>(this));
        taoHttpClient.request();
    }

    /**
     * @param data JSON data
     */
    @Override
    public void onSuccess(Object data) {
        mListener.onLoadDataCompleted((com.alibaba.fastjson.JSONObject) data);
    }

    @Override
    public void onFailure() {
        mListener.onFailure();
    }
}
