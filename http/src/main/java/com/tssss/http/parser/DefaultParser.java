package com.tssss.http.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tssss.http.request.IRequest;
import com.tssss.http.response.IResponse;
import com.tssss.http.result.IResult;
import com.tssss.http.result.Result;

/**
 * 利用 FastJson 将字符串转换为 java 对象
 */
public class DefaultParser implements IParser {
    private static DefaultParser instance;

    private DefaultParser() {
    }

    public static DefaultParser getInstance() {
        if (instance == null)
            instance = new DefaultParser();
        return instance;
    }

    @Override
    public IResult parseResponse(IRequest request, IResponse iResponse) {
        String body = iResponse.getBodyString();
        Log.wtf(getClass().getSimpleName(), body);
        Object o = null;
        try {
            o = JSON.parseObject(body, request.getClass());
        } catch (Exception e) {
            Log.wtf(getClass().getSimpleName(), "服务器返回的字符串不符合 json 语法格式");
        }
        return Result.success(o);
    }
}
