package com.tssss.bysj.login.model;

import com.tssss.bysj.base.BaiduRequest;
import com.tssss.http.annoation.RequestMethod;
import com.tssss.http.request.IRequest;

public interface LoginRequest {
    // 测试，将登录请求发送到百度服务器
    IRequest loginRequest = BaiduRequest.sendHttp("", RequestMethod.GET);
}
