package com.tssss.http.okhttp;

import android.util.Log;

import com.tssss.http.HttpScheduler;
import com.tssss.http.annoation.RequestMethod;
import com.tssss.http.request.IRequest;
import com.tssss.http.request.call.ICall;

import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpScheduler extends HttpScheduler {
    private OkHttpClient client;

    @Override
    public ICall newCall(IRequest request) {
        Map<String, Object> parameter = request.getParameter();
        int requestMethod = request.getRequestMethod();
        Request.Builder builder = new Request.Builder();
        switch (requestMethod) {
            case RequestMethod.GET:
                // 拼接 URL
                StringBuilder url = new StringBuilder(request.getHost().getHost());
                url.append(request.getPath());
                // 使用 OkHttp3 的 HttpUrl 类拼接参数
                Log.wtf(getClass().getSimpleName(), url.toString());
                HttpUrl.Builder okHttpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(url.toString())).newBuilder();
                if (parameter != null && parameter.size() > 0) {
                    for (Map.Entry<String, Object> next : parameter.entrySet()) {
                        okHttpUrlBuilder.addQueryParameter(next.getKey(), String.valueOf(next.getValue()));
                    }
                }
                builder.get().url(okHttpUrlBuilder.build());
                break;
            case RequestMethod.POST:
                break;
        }
        Request okHttpRequest = builder.build();
        Call call = getClient().newCall(okHttpRequest);
        // 将 OkHttp 的 Call 转成本框架的 OkHttpCall 然后返回
        return new OkHttpCall(request, call);
    }

    private OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }
}
