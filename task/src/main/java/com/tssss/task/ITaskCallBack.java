package com.tssss.task;

public interface ITaskCallBack<Result> {
    void onComplete(Result result);

    void onException(Throwable throwable);
}
