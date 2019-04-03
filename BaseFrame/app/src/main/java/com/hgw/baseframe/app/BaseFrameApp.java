package com.hgw.baseframe.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hgw.baseframe.constants.Constants;
import com.hgw.baseframe.core.crash.AppUncaughtExceptionHandler;
import com.hgw.baseframe.ui.functionmodule.tts.TTSUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

		//OkHttp初始化
		initOkHttp();
		//腾讯Bugly初始化
		initBugly();
		//APP异常捕获
		initCrash();
	}

	public static synchronized BaseFrameApp getInstance() {
		return instance;
	}

	/**OkHttp初始化*/
	private void initOkHttp() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(30000L, TimeUnit.MILLISECONDS); //毫秒
		builder.readTimeout(30000L, TimeUnit.MILLISECONDS); //毫秒
		builder.writeTimeout(30000L, TimeUnit.MILLISECONDS); //毫秒
		builder.retryOnConnectionFailure(true); //错误重连

		/*https配置，下面三种情况，选择一种*/
//		/**情况一、设置可访问所有的https网站*/
//		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//		builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//
//      	/**情况二、设置具体的证书*/
//      	HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(证书的inputstream, null, null);
//		builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//
//      	/**情况三、双向认证*/
//      	HttpsUtils.SSLParams sslParams =HttpsUtils.getSslSocketFactory(证书的inputstream, 本地证书的inputstream, 本地证书的密码);
//		builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

		OkHttpClient okHttpClient = builder.build();
		OkHttpUtils.initClient(okHttpClient);
	}

	/**腾讯Bugly初始化*/
	private void initBugly() {
		CrashReport.initCrashReport(getApplicationContext(), Constants.BuglyAPPID, false);
	}

	/** 捕捉异常*/
	private void initCrash() {
		AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	/**解决方法数超过64k*/
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

}