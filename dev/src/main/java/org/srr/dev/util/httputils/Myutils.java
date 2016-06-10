package org.srr.dev.util.httputils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenjy on 2015/12/4.
 */
public class Myutils {
    //获取beforDate天前的日期，正则表达自定义型
    public static String getDate(String pattern, int beforeDate){
        Date date = new Date();
        date.setDate(date.getDate() - beforeDate);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(date);
        if("1".equals(format)){
            sdf = new SimpleDateFormat("M月d日");
            format = sdf.format(date);
        }
        return format;
    }
}
