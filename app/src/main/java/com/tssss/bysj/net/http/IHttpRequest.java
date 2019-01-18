package com.tssss.bysj.net.http;

/**
 * HTTP request
 *
 * @author Tssss
 * @date 2019-01-07
 */
public interface IHttpRequest<T> {
    void setUrl(String url);

    void setRequestParam(T requestParam);

    void setHttpResponse(IHttpResponse iHttpResponse);

    /**
     * Execute request actually.
     */
    void execute();

}
