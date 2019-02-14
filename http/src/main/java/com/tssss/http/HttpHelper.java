package com.tssss.http;

import com.tssss.http.okhttp.OkHttpScheduler;
import com.tssss.http.request.IRequest;
import com.tssss.http.request.call.ICall;
import com.tssss.http.result.IResult;

import java.util.Map;

public class HttpHelper {
    private volatile static HttpScheduler httpScheduler;

    public static HttpScheduler getHttpScheduler() {
        if (httpScheduler == null) {
            synchronized (HttpHelper.class) {
                if (httpScheduler == null) {
                    httpScheduler = new OkHttpScheduler();
                }
            }
        }
        return httpScheduler;
    }

    protected static <T> IResult<T> execute(IRequest request, Map<String, Object> params) {
        request.setParameter(params);
        ICall call = getHttpScheduler().newCall(request);
        return getHttpScheduler().execute(call);
    }
}
