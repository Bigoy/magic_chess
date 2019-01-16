package com.tssss.bysj.net.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Complete request task.
 *
 * @author Tssss
 * @date 2019-01-07
 */
class HttpTask implements Runnable {
    private IHttpRequest mHttpRequest;
    private IHttpResponse mHttpResponse;

    public <T> HttpTask(String url, T requestParam, IHttpRequest httpRequest,
                        IHttpResponse httpListener) {
        mHttpRequest = httpRequest;
        mHttpResponse = httpListener;
        mHttpRequest.setUrl(url);
        mHttpRequest.setHttpResponse(mHttpResponse);

        if (requestParam != null) {
            String rp = JSON.toJSONString(requestParam);
            try {
                mHttpRequest.setRequestParam(rp.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                mHttpResponse.onFailure();

                Log.wtf(getClass().getSimpleName(), "ERROR");
            }
        }

    }

    /**
     * Execute HTTP request actually
     */
    @Override
    public void run() {
        mHttpRequest.execute();
    }
}
