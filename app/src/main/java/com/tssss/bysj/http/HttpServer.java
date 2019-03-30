package com.tssss.bysj.http;

import com.tssss.bysj.http.request.IRequest;
import com.tssss.bysj.http.result.IResult;

import java.util.Map;


public class HttpServer {
    protected <T> IResult<T> execute(IRequest request, Map<String, Object> params) {
        return HttpHelper.execute(request, params);
    }
}
