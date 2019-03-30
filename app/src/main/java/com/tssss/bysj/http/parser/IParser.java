package com.tssss.bysj.http.parser;


import com.tssss.bysj.http.request.IRequest;
import com.tssss.bysj.http.response.IResponse;
import com.tssss.bysj.http.result.IResult;

public interface IParser {
    IResult parseResponse(IRequest request, IResponse iResponse);
}
