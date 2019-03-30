package com.tssss.bysj.http.request.call;

import com.tssss.bysj.http.request.IRequest;
import com.tssss.bysj.http.response.IResponse;

public interface ICall {
    IResponse execute();

    IRequest getRequest();
}
