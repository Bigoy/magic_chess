package com.tssss.bysj.http;

import com.tssss.bysj.http.parser.IParser;
import com.tssss.bysj.http.request.IRequest;
import com.tssss.bysj.http.request.call.ICall;
import com.tssss.bysj.http.response.IResponse;
import com.tssss.bysj.http.result.IResult;

public abstract class HttpScheduler {
    public abstract ICall newCall(IRequest request);

    public IResult execute(ICall call) {
        //IResponse 和  IResult 进行一个转换
        IResponse iResponse = call.execute();
        IRequest request = call.getRequest();
        IParser parser = request.getParser();
        return parser.parseResponse(request, iResponse);
    }
}
