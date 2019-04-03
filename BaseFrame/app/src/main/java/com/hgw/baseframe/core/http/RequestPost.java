package com.hgw.baseframe.core.http;

import android.content.Context;

import com.hgw.baseframe.constants.RequestData;
import com.hgw.baseframe.util.LogHelper;
import com.hgw.baseframe.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 描述：post请求
 * @author hgw
 */

public class RequestPost {
    private Context mContext;
    /**Post请求触发接口*/
    private RequestCallback mRequestCallback;

    public RequestPost(Context mContext, String url, Map<String,String> params){
        this.mContext=mContext;
        post(url,params);
    }

    public RequestPost(Context mContext){
        this.mContext=mContext;
    }
    /**
     * post
     * @param url 地址
     * @param params 参数
     * */
    public void post(String url, Map<String,String> params){
        OkHttpUtils.post()
                .url(url)
                .headers(RequestData.getHeaders())
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogHelper.showLog("请求地址="+url);
                        LogHelper.showLog("请求参数="+params);
                        LogHelper.showLog("请求异常结果="+e.getMessage());
                        ToastUtil.showShortToast(mContext,"请求异常，请稍后重试");
                        if(mRequestCallback!=null){
                            mRequestCallback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogHelper.showLog("请求地址="+url);
                        LogHelper.showLog("请求参数="+params);
                        LogHelper.showLog("请求结果="+response);
                        if(mRequestCallback!=null){
                            mRequestCallback.onSuccess(response);
                        }
                    }
                });
    }

    /**注册Post请求触发接口*/
	public void setRequestCallback(RequestCallback listener) {
        mRequestCallback = listener;
	}

	/**定义Post请求触发接口*/
	public interface RequestCallback {
        void onSuccess(String response);
        void onError(String response);
	}

}
