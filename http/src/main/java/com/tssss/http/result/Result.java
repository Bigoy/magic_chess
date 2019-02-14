package com.tssss.http.result;

public class Result<T> implements IResult<T> {
    // 常用网络请求响应结果码
    public final static int CODE_200 = 200;
    public final static int CODE_404 = 404;
    public final static int CODE_504 = 504;
    public final static int CODE_505 = 505;

    protected boolean isSuccess;
    protected T data;
    protected int code;
    protected String msg;


    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public int getResultCode() {
        return code;
    }

    @Override
    public T data() {
        return data;
    }

    public static IResult success(Object o) {
        Result result = new Result();
        result.code = CODE_200;
        result.isSuccess = true;
        result.data = o;
        return result;
    }

    public static IResult fail(int code) {
        Result result = new Result();
        result.code = code;
        result.isSuccess = false;
        return result;
    }
}
