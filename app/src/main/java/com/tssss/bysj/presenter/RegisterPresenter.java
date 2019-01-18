package com.tssss.bysj.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnRegisterListener;
import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.User;

public class RegisterPresenter extends PresenterImp implements IDataListener {
    private OnRegisterListener mListener;

    public void requestRegister(User user, OnRegisterListener listener) {
        mListener = listener;

        TaoHttpClient client = new TaoHttpClient(HttpConstant.BASE_URL, JSON.toJSON(user),
                new JsonHttpRequest(), new JsonHttpResponse(this));
        client.request();
    }

    /**
     * @param data jsonObject
     */
    @Override
    public void onSuccess(Object data) {
        mListener.onSuccess();

        Log.wtf(getClass().getSimpleName(), JSON.toJSONString(data));
    }

    @Override
    public void onFailure() {
        mListener.onFail();
    }
}
