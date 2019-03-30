package com.tssss.bysj.http.annoation;

import androidx.annotation.IntDef;


@IntDef({RequestMethod.GET, RequestMethod.POST})
public @interface RequestMethod {
    int GET = 0;
    int POST = 1;
}
