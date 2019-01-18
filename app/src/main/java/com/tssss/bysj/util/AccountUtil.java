package com.tssss.bysj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountUtil {
    public static boolean validAccount(String phone, String password) {
        return inspectPhone(phone) && isValidPassword(password);
    }

    private static boolean inspectPhone(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[2,8])|(18[0,5-9]))\\d{8}$");
        Matcher m = pattern.matcher(phone);

        if (phone.length() == 11 && !phone.contains(" ")) {
            return m.matches() && !StringUtil.isBlank(phone) && StringUtil.isNumber(phone);
        }

        return false;
    }

    private static boolean isValidPassword(String password) {
        return password.length() == 16 && !StringUtil.isBlank(password);
    }
}
