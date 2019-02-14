package com.tssss.http.parser;

import com.tssss.http.request.IRequest;
import com.tssss.http.response.IResponse;
import com.tssss.http.result.IResult;

public interface IParser {
    IResult parseResponse(IRequest request, IResponse iResponse);
}
