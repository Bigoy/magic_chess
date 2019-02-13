package com.tssss.http;

import java.util.Map;

public interface IRequest {
    void setParameter(Map<String, Object> parameter);

    IHost getHost();

    Map<String, Object> getParameter();

    int getRequestMethod();
}
