package com.tssss.bysj.net.http;

/**
 * Complete request task.
 *
 * @author Tssss
 * @date 2019-01-07
 */
class HttpTask implements Runnable {
    private IHttpRequest mHttpRequest;
    private IHttpResponse mHttpResponse;

    <T> HttpTask(String url, T requestParam, IHttpRequest httpRequest,
                 IHttpResponse httpListener) {
        mHttpRequest = httpRequest;
        mHttpResponse = httpListener;

        mHttpRequest.setUrl(url);
        mHttpRequest.setHttpResponse(mHttpResponse);

        if (requestParam != null) {
            mHttpRequest.setRequestParam(requestParam);
        }

    }

    /**
     * Execute HTTP request actually
     */
    @Override
    public void run() {
        mHttpRequest.execute();
    }
}
