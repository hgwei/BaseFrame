package com.hgw.baseframe.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 描述：Toast工具类 （解决短时间相同内容的Toast重复弹出）
 * @author hgw
 */
public class ToastUtil {
    private static String oldMsg;
    private static long time;

    public static void showToast(Context context, String msg, int duration) {
        if (!msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(context, msg, duration).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于3秒时才显示
            if (System.currentTimeMillis() - time > 3000) {
                Toast.makeText(context, msg, duration).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;
    }

    /**
     * 显示Toast（短）
     * */
    public static void showShortToast(Context context, String msg){
        showToast(context,msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast（长）
     * */
    public static void showLongToast(Context context, String msg){
        showToast(context,msg, Toast.LENGTH_LONG);
    }

}