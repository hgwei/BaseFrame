package com.hgw.baseframe.ui.http.retrofit.core;

import com.hgw.baseframe.BuildConfig;
import com.hgw.baseframe.app.BaseFrameApp;
import com.hgw.baseframe.constants.UrlConstants;
import com.hgw.baseframe.util.DirUtil;
import com.hgw.baseframe.util.MethodCommon;
import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述：AppHttpClient（配置）
 * @author hgw
 */
public class AppHttpClient {
    public static Retrofit retrofit = null;

    public static Retrofit retrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存
             */
            File cacheFile = new File(DirUtil.PATH_CACHE);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!MethodCommon.isNetworkConnected(BaseFrameApp.getInstance())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (MethodCommon.isNetworkConnected(BaseFrameApp.getInstance())) {
                        int maxAge = 0;
                        // 有网络时, 不缓存, 最大保存时长为0,单位:秒
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    } else {
                        // 无网络时，设置超时为4周,单位:秒
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("Pragma")
                                .build();
                    }
                    return response;
                }
            };
            builder.cache(cache).addInterceptor(cacheInterceptor);

//            /**
//             *  公共参数
//             */
//            Interceptor addQueryParameterInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request request;
//                    String method = originalRequest.method();
//                    Headers headers = originalRequest.headers();
//                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                            // Provide your custom parameter here
//                            .addQueryParameter("platform", "android")
//                            .addQueryParameter("version", "1.0.0")
//                            .build();
//                    request = originalRequest.newBuilder().url(modifiedUrl).build();
//                    return chain.proceed(request);
//                }
//            };
//            builder.addInterceptor(addQueryParameterInterceptor);

//            /**
//             * 设置头
//             */
//            Interceptor headerInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request.Builder requestBuilder = originalRequest.newBuilder()
//                            .header("AppType", "TPOS")
//                            .header("Content-Type", "application/json")
//                            .header("Accept", "application/json")
//                            .method(originalRequest.method(), originalRequest.body());
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            };
//            builder.addInterceptor(headerInterceptor );

            /**
             * Log信息拦截器
             */
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }

            /**
             * 设置cookie
             */
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            builder.cookieJar(new JavaNetCookieJar(cookieManager));

            /**
             * 设置超时和重连
             */
            //设置超时
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            //以上设置结束，才能build()
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstants.SERVERURL)
                    //设置 Json 转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    //RxJava 适配器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;

    }

}
