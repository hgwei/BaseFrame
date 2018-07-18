package com.hgw.baseframe.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 描述：日志工具类
 * @author hgw
 */
public class LogHelper {
    /**是否开启日志*/
    private static boolean isLogEnable = true;
    /**日志输出TAG*/
    private static String tag = "Test";

    /**
     * 输出"Test"标志日志
     * @param message 输出内容
     * */
    public static void showLog(String message){
        if(isLogEnable){
            Log.e(tag,message);
        }
    }

    /**
     * 输出自定义TAG日志
     * @param tag TAG
     * @param message 输出内容
     * */
    public static void showLog(String tag,String message){
        if(isLogEnable){
            Log.e(tag,message);
        }
    }

    /**
     * 输出长日志（分段输出）
     * @param message 输出内容
     */
    public static void showLongLog(String message) {
        if (!TextUtils.isEmpty(message)) {
            int maxLogSize = 3000;
            int start;
            int end;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                start = i * maxLogSize;
                end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.e(tag,message.substring(start, end));
            }
        }
    }
}
