package com.tssss.bysj.http.request;

import com.tssss.bysj.http.parser.IParser;
import com.tssss.bysj.http.request.host.IHost;

import java.util.Map;

public interface IRequest {
    void setParameter(Map<String, Object> parameter);

    IHost getHost();

    Map<String, Object> getParameter();

    int getRequestMethod();

    IParser getParser();

    String getPath();
}
