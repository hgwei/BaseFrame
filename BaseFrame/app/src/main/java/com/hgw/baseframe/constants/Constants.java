package com.hgw.baseframe.constants;

import android.os.Environment;

/**
 * 描述：常量
 * @author hgw
 * */
public class Constants {

    /**SharedPreferences文件名*/
    public static final String PREFERENCE_NAME = "baseframe_shared_preferences";

    /**APP文件根目录地址*/
    public static final String FILEROOTPATH = Environment.getExternalStorageDirectory().getPath() + "/BaseFrame";

    /**Bugly APPID*/
    public static final String BuglyAPPID = "c10da7940d";


    /**
     * 广播部分
     * */
    //未读已读消息数改变广播
    public static final String BROADCAST_MESSAGE_NUM_CHANGE = "BroadCast_Message_Num_Change";

}
