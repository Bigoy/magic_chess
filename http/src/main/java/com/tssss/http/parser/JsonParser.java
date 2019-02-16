package com.tssss.http.parser;

import com.alibaba.fastjson.JSON;
import com.tssss.http.request.IRequest;
import com.tssss.http.response.IResponse;
import com.tssss.http.result.IResult;
import com.tssss.http.result.Result;

/**
 * 利用 FastJson 将字符串转换为 java 对象
 */
public class JsonParser implements IParser {
    private static JsonParser instance;

    private JsonParser() {
    }

    public static JsonParser getInstance() {
        if (instance == null)
            instance = new JsonParser();
        return instance;
    }

    @Override
    public IResult parseResponse(IRequest request, IResponse iResponse) {
        String body = iResponse.getBodyString();
//        Log.wtf(getClass().getSimpleName(), body);
        Object o = null;
        try {
            o = JSON.parseObject(body, request.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(o);
    }
}
