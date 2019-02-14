package com.tssss.http.request;

import com.tssss.http.parser.IParser;
import com.tssss.http.request.host.IHost;

import java.util.Map;

public class Request implements IRequest {
    protected Map<String, Object> parameter;
    protected IHost host;
    protected int requestMethod;
    protected IParser parser;
    protected String path;

    @Override
    public void setParameter(Map<String, Object> parameter) {
        this.parameter = parameter;
    }

    @Override
    public IHost getHost() {
        return this.host;
    }

    @Override
    public Map<String, Object> getParameter() {
        return this.parameter;
    }

    @Override
    public int getRequestMethod() {
        return this.requestMethod;
    }

    @Override
    public IParser getParser() {
        return this.parser;
    }

    @Override
    public String getPath() {
        return this.path;
    }
}
