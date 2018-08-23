package com.hgw.baseframe.core.http.rx;

import android.content.Context;
import android.widget.Toast;
import com.hgw.baseframe.app.BaseFrameApp;
import com.hgw.baseframe.bean.BaseResponse;
import com.hgw.baseframe.util.LogHelper;
import com.hgw.baseframe.view.LoadingProgressDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 描述：BaseObserver
 * @author hgw
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private Context mContext;
    /**是否显示加载弹窗，默认显示*/
    private boolean isShowDialog=true;

    protected BaseObserver(Context mContext) {
        this.mContext=mContext;
    }

    protected BaseObserver(Context mContext, boolean isShowDialog) {
        this.isShowDialog=isShowDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        //请求开始前执行
        if(isShowDialog){
            LoadingProgressDialog.showProgressDialog(mContext,"加载中...");
        }
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        //这里可以对返回结果做统一拦截处理，看具体业务需求制定
        LoadingProgressDialog.hideProgressDialog();
        if (response.getErrorCode()==0) {
            //T t = response.getData();
            onHandleSuccess(response);
        } else {
            Toast.makeText(BaseFrameApp.getInstance(),response.getErrorMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable e) {
        //请求失败执行
        LoadingProgressDialog.hideProgressDialog();
        LogHelper.showLog("请求失败："+ e.toString());
    }

    @Override
    public void onComplete() {
        //请求完成执行
        LogHelper.showLog("请求完成执行");
    }


    protected abstract void onHandleSuccess(BaseResponse<T> response);
}