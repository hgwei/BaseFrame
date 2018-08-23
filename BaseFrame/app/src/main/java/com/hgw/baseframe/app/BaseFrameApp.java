package com.hgw.baseframe.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 描述：Application
 * @author hgw
 * */
public class BaseFrameApp extends Application {
	private static BaseFrameApp instance;
	public static boolean isFirstRun=true;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		//腾讯Bugly初始化
		initBugly();
	}

	public static synchronized BaseFrameApp getInstance() {
		return instance;
	}

	/**腾讯Bugly初始化*/
	private void initBugly() {
		CrashReport.initCrashReport(getApplicationContext(), Constants.BuglyAPPID, false);
	}

	/**解决方法数超过64k*/
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

}