package com.hgw.baseframe.core.http;

/**
 * 描述：HttpHelper
 *
 * @author hgw
 */

public class HttpHelper {
    private static HttpHelper mHttpHelper=null;

    public static HttpHelper getInstance(){
        if (mHttpHelper == null) {
            mHttpHelper = new HttpHelper();
        }
        return mHttpHelper;
    }

    public GeeksApis httpService(){
        return AppHttpClient.retrofit().create(GeeksApis.class);
    }
}
