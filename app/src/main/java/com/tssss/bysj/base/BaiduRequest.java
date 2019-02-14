package com.tssss.bysj.base;

import com.tssss.http.annoation.RequestMethod;
import com.tssss.http.parser.DefaultParser;
import com.tssss.http.request.IRequest;
import com.tssss.http.request.Request;

/**
 * 测试
 */
public class BaiduRequest extends Request {
    public static IRequest sendHttp(String path, @RequestMethod int requestMethod) {
        BaiduRequest request = new BaiduRequest();
        request.host = HostManager.baiduHost;
        request.path = path;
        request.requestMethod = requestMethod;
        request.parser = DefaultParser.getInstance();
        return request;
    }
}
