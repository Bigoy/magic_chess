package com.tssss.http;

import com.tssss.http.request.IRequest;
import com.tssss.http.result.IResult;

import java.util.Map;

public class HttpServer {
    protected <T> IResult<T> execute(IRequest request, Map<String, Object> params) {
        return HttpHelper.execute(request, params);
    }
}
