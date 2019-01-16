package com.tssss.bysj.net.http;

import java.io.InputStream;

/**
 * Request result listening interface.
 *
 * @author Tssss
 * @date 2019-01-07
 */
public interface IHttpResponse {
    void onSuccess(InputStream is);

    void onFailure();

}
