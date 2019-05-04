package com.tssss.bysj.security;

import android.util.Base64;

import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;

public class Token {
    public static String token() {
        String s = Constant.JMESSAGE_APP_KEY + ":" + Constant.JMESSAGE_MASTER_SECRET;
        String base64Str = Base64.encodeToString(s.trim().getBytes(), Base64.NO_WRAP);
        Logger.log(base64Str);
        return base64Str;

    }
}
