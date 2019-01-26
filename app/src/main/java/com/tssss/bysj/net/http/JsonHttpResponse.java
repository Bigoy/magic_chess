package com.tssss.bysj.net.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

public class JsonHttpResponse implements IHttpResponse {
    private IDataListener mDataListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonHttpResponse(IDataListener dataListener) {
        if (dataListener != null)
            this.mDataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream is) {
        try {
            final String str = StringUtil.convertInputStream(is);
            final JSONObject response = (JSONObject) JSON.toJSON(str);

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
            if (mDataListener != null)
                mDataListener.onFailure();
        }
    }

    @Override
    public void onFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDataListener != null)
                    mDataListener.onFailure();
            }
        });
    }

}
