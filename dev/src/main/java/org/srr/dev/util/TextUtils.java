package org.srr.dev.util;

public class TextUtils {


    /**
     * String 为空或者长度为0或 "null" 时返回true
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || "\"\"".equals(str) || "null".equals(str) || str.length() == 0;
    }

    /**
     * String 为空或者长度为0或 "null" 时返回
     */
    public static String isEmptyString(CharSequence str) {
        if (str == null || "\"\"".equals(str.toString()) || "null".equals(str.toString()) || str.length() == 0 || "".equals(str.toString())) {
            return "未知";
        } else {
            return str.toString();
        }
    }

    /**
     * String 为空或者长度为0或 "null" 时返回
     */
    public static String isEmptyAge(int age) {
        if (age > 0) {
            return age + "";
        } else {
            return "保密";
        }
    }


}
