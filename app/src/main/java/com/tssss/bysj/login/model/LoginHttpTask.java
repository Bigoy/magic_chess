package com.tssss.bysj.login.model;

import com.tssss.http.HttpServer;
import com.tssss.http.result.IResult;

import java.util.HashMap;
import java.util.Map;

public class LoginHttpTask<T> extends HttpServer {
    public IResult<T> test() {
        Map<String, Object> parameter = new HashMap<>();
        return super.execute(LoginRequest.loginRequest, parameter);
    }
}
