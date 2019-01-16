package com.tssss.bysj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    public static boolean isPhoneNumber(long account) {
        String s = String.valueOf(account);
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[2,8])|(18[0,5-9]))\\d{8}$");
        Matcher m = pattern.matcher(s);
        if (s.length() == 11 && !s.contains(" ")) {
            return m.matches();
        }
        return false;
    }

}
