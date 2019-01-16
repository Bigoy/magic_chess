package com.tssss.bysj.net.http;

public interface IDataListener<T> {
    void onSuccess(T data);

    void onFailure();
}
