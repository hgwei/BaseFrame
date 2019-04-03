package com.hgw.baseframe.core.http;

import android.content.Context;
import android.widget.Toast;

import com.hgw.baseframe.constants.RequestData;
import com.hgw.baseframe.util.LogHelper;
import com.hgw.baseframe.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 描述：post Json请求
 * @author hgw
 */

public class RequestPostJson {
    private Context mContext;
    /**Post Json请求触发接口*/
    private RequestCallback mRequestCallback;

    public RequestPostJson(Context mContext, String url, String jsonString){
        this.mContext=mContext;
        post(url,jsonString);
    }

    public RequestPostJson(Context mContext){
        this.mContext=mContext;
    }
    /**
     * post
     * @param url 地址
     * @param jsonString 参数
     * */
    public void post(String url, String jsonString){
        LogHelper.showLog("请求地址="+url);
        LogHelper.showLog("请求参数="+jsonString);
        OkHttpUtils.postString()
                .url(url)
                .headers(RequestData.getHeaders())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(jsonString) //参数jsonString
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogHelper.showLog("请求地址="+url);
                        LogHelper.showLog("请求参数="+jsonString);
                        LogHelper.showLog("请求异常结果="+e.getMessage());
                        ToastUtil.showShortToast(mContext,"请求异常，请稍后重试");
                        if(mRequestCallback!=null){
                            mRequestCallback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogHelper.showLog("请求地址="+url);
                        LogHelper.showLog("请求参数="+jsonString);
                        LogHelper.showLog("请求结果="+response);
                        if(mRequestCallback!=null){
                            mRequestCallback.onSuccess(response);
                        }
                    }
                });
    }

    /**注册Post Json请求触发接口*/
	public void setRequestCallback(RequestCallback listener) {
        mRequestCallback = listener;
	}

	/**定义Post Json请求触发接口*/
	public interface RequestCallback {
        void onSuccess(String response);
        void onError(String response);
	}

}
