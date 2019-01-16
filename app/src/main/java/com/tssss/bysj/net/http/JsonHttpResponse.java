package com.tssss.bysj.net.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

public class JsonHttpResponse<T> implements IHttpResponse {
    Class<T> responseClass;
    private IDataListener mDataListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonHttpResponse(IDataListener dataListener) {
        this.mDataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream is) {
        try {
            String str = StringUtil.convertInputStream(is);
            final T response = JSON.parseObject(str, responseClass);

            if (mDataListener != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDataListener.onSuccess(response);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
            mDataListener.onFailure();
        }
    }

    @Override
    public void onFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mDataListener.onFailure();
            }
        });
    }


}
