package com.tssss.bysj.net.http;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonPostHttpRequest implements IHttpRequest {
    private String mUrl;
    private IHttpResponse mResponse;
    private byte[] mRequestParam;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void setRequestParam(byte[] requestParam) {
        this.mRequestParam = requestParam;
    }

    @Override
    public void setHttpResponse(IHttpResponse iHttpResponse) {
        this.mResponse = iHttpResponse;
    }

    /**
     * User HttpUrlConnection to access server actually
     */
    @Override
    public void execute() {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setConnectTimeout(20000);
            conn.connect();

            /*
            write paramData
             */
            OutputStream os = conn.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(mRequestParam);
            bos.flush();
            bos.close();
            os.close();

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
