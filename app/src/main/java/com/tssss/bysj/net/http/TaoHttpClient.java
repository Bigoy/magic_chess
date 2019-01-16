package com.tssss.bysj.net.http;

/**
 * All callers should instantiate TaoHttpClient object and invoke request()
 * to initiate http request
 *
 * @author Tssss
 * @date 2019-01-08
 * @process TaoHttpClient taoHttpClient = new TaoHttpClient()
 * taoHttpClient.request()
 */
public class TaoHttpClient<T> {
    private String mUrl;
    private T mRequestParam;
    private IHttpRequest mHttpRequest;
    private IHttpResponse mHttpResponse;

    public TaoHttpClient(String url, T requestParam, IHttpRequest httpRequest,
                         IHttpResponse httpResponse) {
        this.mUrl = url;
        this.mRequestParam = requestParam;
        this.mHttpRequest = httpRequest;
        this.mHttpResponse = httpResponse;
    }

    /**
     * Add task to thread pool
     */
    public void request() {
        HttpTask httpTask = new HttpTask(mUrl, mRequestParam, mHttpRequest, mHttpResponse);

        HttpThreadPool httpThreadPool = HttpThreadPool.getInstance();
        httpThreadPool.addHttpTask(httpTask);
    }
}
