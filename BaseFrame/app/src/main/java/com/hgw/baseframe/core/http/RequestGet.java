package com.hgw.baseframe.core.http;

import android.content.Context;
import android.widget.Toast;

import com.hgw.baseframe.constants.RequestData;
import com.hgw.baseframe.util.LogHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

/**
 * 描述：get请求
 * @author hgw
 */

public class RequestGet {
    private Context mContext;
    /**Get请求触发接口*/
    private RequestCallback mRequestCallback;

    public RequestGet(Context mContext, String url){
        this.mContext=mContext;
        get(url);
    }

    public RequestGet(Context mContext){
        this.mContext=mContext;
    }

    /**
     * get
     * @param url 地址
     * */
    public void get(String url){
        OkHttpUtils.get()
                .url(url)
                .headers(RequestData.getHeaders())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogHelper.showLog("请求地址="+url);
                        LogHelper.showLog("请求异常结果="+e.getMessage());
                        //Toast.makeText(mContext,"请求异常", Toast.LENGTH_SHORT).show();
                        if(mRequestCallback!=null){
                            mRequestCallback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogHelper.showLog("请求地址="+url);
                        LogHelper.showLog("请求结果="+response);
                        if(mRequestCallback!=null){
                            mRequestCallback.onSuccess(response);
                        }
                    }
                });
    }

    /**注册Get请求触发接口*/
	public void setRequestCallback(RequestCallback listener) {
        mRequestCallback = listener;
	}

	/**定义Get请求触发接口*/
	public interface RequestCallback {
        void onSuccess(String response);
        void onError(String response);
	}

}
