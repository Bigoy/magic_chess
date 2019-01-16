package com.tssss.bysj.util;

public class PasswordUtil {
    public static boolean isValidPassword(String password) {
        return password.length() == 16;
    }

    public static String filterPassword(String password) {
        if (password.length() > 16) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(password.charAt(i));
            }
            return sb.toString();
        }
        return password;
    }
}
