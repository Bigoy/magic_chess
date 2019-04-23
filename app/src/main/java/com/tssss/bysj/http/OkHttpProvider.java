package com.tssss.bysj.http;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.exception.NetException;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.other.VersionManager;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.SystemUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpProvider extends BaseHttpProvider {
    private static OkHttpProvider instance;

    private OkHttpProvider() {
    }

    public static OkHttpProvider getInstance() {
        if (null == instance) {
            instance = new OkHttpProvider();
        }
        return instance;
    }

    @Override
    public String requestGet(String url, Map<String, ?> param) throws NetException {
        String result = "";
        if (!SystemUtil.checkNet() || StringUtil.isBlank(url)) {
            throw new NetException();
        } else {
            StringBuilder finalUrl = new StringBuilder(url);
            // 判定是否已有参数
            if (url.contains("?")) {
                finalUrl.append("&version=");
            } else {
                finalUrl.append("?version=");
            }
            finalUrl.append(VersionManager.getInstance().getVersion());
            // 参数拼接
            if (null != param && param.size() > 0) {
                for (Map.Entry<String, ?> entry : param.entrySet()) {
                    finalUrl.append("&").append(entry.getKey()).append("=");
                    if (entry.getValue() instanceof String) {
                        String value = (String) entry.getValue();
                        if (!StringUtil.isBlank(value)) {
                            try {
                                finalUrl.append(URLEncoder.encode(
                                        (String) entry.getValue(), "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (entry.getValue() != null) {
                        finalUrl.append(entry.getValue());
                    }
                }
            }
            Request request = HttpRequestFactory.okhttpGetRequest(finalUrl.toString());
            try {
                Response response = HttpClientFactory.okHttpClient().newCall(request).execute();
                if (null != response.body()) {
                    result = response.body().string();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public void requestAsyncGet(String url, Map<String, ?> param, HttpCallback callback) {
        if (!SystemUtil.checkNet()) {
            if (null != callback) {
                callback.onFailure("no available net");
            }
        } else if (StringUtil.isBlank(url)) {
            if (null != callback) {
                callback.onFailure("url is illegal");
            }
        } else {
            StringBuilder finalUrl = new StringBuilder(url);
            // 判定是否已有参数
            if (url.contains("?")) {
                finalUrl.append("&version=");
            } else {
                finalUrl.append("?version=");
            }
            finalUrl.append(VersionManager.getInstance().getVersion());
            // 参数拼接
            if (null != param && param.size() > 0) {
                for (Map.Entry<String, ?> entry : param.entrySet()) {
                    finalUrl.append("&").append(entry.getKey()).append("=");
                    if (entry.getValue() instanceof String) {
                        String value = (String) entry.getValue();
                        if (!StringUtil.isBlank(value)) {
                            try {
                                finalUrl.append(URLEncoder.encode(
                                        (String) entry.getValue(), "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (entry.getValue() != null) {
                        finalUrl.append(entry.getValue());
                    }
                }
            }
            Logger.log(finalUrl.toString());
            Request request = HttpRequestFactory.okhttpGetRequest(finalUrl.toString());
            HttpClientFactory.okHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (null != callback) {
                        callback.onFailure("http request failed");
                        Logger.log("http request failed");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (null != callback) {
                        if (response.body() != null) {
                            String result = response.body().string();
                            callback.onSuccess(result);
                            Logger.log(result);
                        } else {
                            callback.onFailure("服务器未返回任何内容");
                            Logger.log("服务器未返回任何内容");
                        }
                    }
                }
            });
        }
    }

    @Override
    public void requestPost(String url, Map<String, ?> param, HttpCallback callback) {
        if (!SystemUtil.checkNet()) {
            if (null != callback) {
                callback.onFailure("no available net");
            }
        } else if (StringUtil.isBlank(url)) {
            if (null != callback) {
                callback.onFailure("url is illegal");
            }
        } else {
            String postContent = "";
            if (null != param && param.size() > 0) {
                postContent = JSON.toJSONString(param);
                Logger.log(postContent);
            }
            Request request = HttpRequestFactory.okhttpPostStringRequest(url, postContent);
            HttpClientFactory.okHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (null != callback) {
                        callback.onFailure("http request failed");
                        Logger.log("http request failed");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (null != callback) {
                        if (response.body() != null) {
                            String result = response.body().string();
                            callback.onSuccess(result);
                            Logger.log(result);
                        } else {
                            callback.onFailure("服务器未返回任何内容");
                            Logger.log("服务器未返回任何内容");
                        }
                    }
                }
            });

        }
    }
}
