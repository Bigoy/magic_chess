package com.tssss.http.annoation;

import androidx.annotation.IntDef;

import static com.tssss.http.annoation.RequestMethod.GET;
import static com.tssss.http.annoation.RequestMethod.POST;

@IntDef({GET, POST})
public @interface RequestMethod {
    int GET = 0;
    int POST = 1;
}
