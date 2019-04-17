package com.tssss.bysj.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {
    public static void getAsync(@NonNull String url, com.tssss.bysj.http.Callback callback) {
        if (!url.contains("http://")) {
            url = "http://" + url;
        }
        String finalUrl = url;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (null != callback) {
                    callback.onFailure();
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    if (null != callback) {
                        callback.onSuccess(response.body().string());
                    }
                } else {
                    if (null != callback) {
                        callback.onFailure();
                    }
                }
            }
        });
    }

    public static String getSync(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.SECONDS)
                .build();

        if (!url.contains("http://")) {
            url = "http://" + url;
        }

        Call call = client.newCall(new Request.Builder()
                .get()
                .url(url)
                .build());
        String result = "";
        try {
            result = call.execute().body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
