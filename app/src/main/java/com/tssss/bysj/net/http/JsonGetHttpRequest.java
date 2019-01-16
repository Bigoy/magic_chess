package com.tssss.bysj.net.http;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonGetHttpRequest implements IHttpRequest {
    private String mUrl;
    private IHttpResponse mResponse;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Do nothing if request method is GET
     */
    @Override
    public void setRequestParam(byte[] requestParam) {
    }

    @Override
    public void setHttpResponse(IHttpResponse iHttpResponse) {
        this.mResponse = iHttpResponse;
    }

    @Override
    public void execute() {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(true);
            conn.setConnectTimeout(20000);
            conn.connect();

            /*
            read data
             */
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                mResponse.onSuccess(is);

                is.close();
            } else {
                Log.wtf(getClass().getSimpleName(), "failed to request, code = "
                        + conn.getResponseCode());

                mResponse.onFailure();
            }

        } catch (Exception e) {
            e.printStackTrace();
            mResponse.onFailure();
        }
    }
}
