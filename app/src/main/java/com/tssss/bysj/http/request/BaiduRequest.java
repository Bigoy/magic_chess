package com.tssss.bysj.http.request;

import com.tssss.bysj.base.HostManager;
import com.tssss.bysj.http.annoation.RequestMethod;
import com.tssss.bysj.http.parser.JsonParser;

/**
 * 测试
 */


public class BaiduRequest extends Request {
    public static IRequest sendHttp(String path, @RequestMethod int requestMethod) {
        BaiduRequest request = new BaiduRequest();
        request.host = HostManager.baiduHost;
        request.path = path;
        request.requestMethod = requestMethod;
        request.parser = JsonParser.getInstance();
        return request;
    }
}
