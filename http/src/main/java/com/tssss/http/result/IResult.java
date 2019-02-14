package com.tssss.http.result;

/**
 * Created by tssss on 2019/2/14.
 * <p>
 * 网络响应的结果
 */
public interface IResult<T> {
    boolean isSuccess();

    int getResultCode();

    T data();
}
