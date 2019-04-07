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
    public void getAsync(@NonNull String url, @NonNull com.tssss.bysj.http.Callback callback) {
        if (!url.contains("http://")) {
            url = "http://" + url;
        }
        String finalUrl = url;
        Runnable r = new Runnable() {
            @Override
            public void run() {
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
                        callback.onFailure();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.body() != null) {
                            callback.onSuccess(response.body().string());
                        }else {
                            callback.onFailure();
                        }
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
