package com.tssss.http.request.call;

import com.tssss.http.request.IRequest;
import com.tssss.http.response.IResponse;

public interface ICall {
    IResponse execute();

    IRequest getRequest();
}
