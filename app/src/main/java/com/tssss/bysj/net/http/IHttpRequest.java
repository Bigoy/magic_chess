package com.tssss.bysj.net.http;

/**
 * HTTP request
 *
 * @author Tssss
 * @date 2019-01-07
 */
public interface IHttpRequest {
    void setUrl(String url);

    void setRequestParam(byte[] requestParam);

    void setHttpResponse(IHttpResponse iHttpResponse);

    /**
     * Execute request actually.
     */
    void execute();

}
