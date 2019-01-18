package com.tssss.bysj.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * @param limitation limited number of characters
     * @param str        user entered string
     * @return filtered string
     */
    public static String filterString(String str, int limitation) {
        StringBuilder sb = new StringBuilder();
        if (str.length() > limitation) {
            for (int i = 0; i < limitation; i++) {
                sb.append(str.charAt(i));
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * Convert InputStream to String
     */
    public static String convertInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    public static boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
