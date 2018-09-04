package com.hgw.baseframe.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.bean.BannerData;
import com.hgw.baseframe.bean.BaseResponse;
import com.hgw.baseframe.bean.LoginData;
import com.hgw.baseframe.core.http.HttpHelper;
import com.hgw.baseframe.core.http.rx.BaseObserver;
import com.hgw.baseframe.core.http.rx.RxSchedulers;
import com.hgw.baseframe.util.LogHelper;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public class RetrofitActivity extends BaseActivity {

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

    }

    /**Get请求示例*/
    public void getBannerClick(View view){
        HttpHelper.getInstance().httpService().getBannerData()
                .compose(RxSchedulers.compose())
                .subscribe(new BaseObserver<List<BannerData>>(this){
                    @Override
                    protected void onHandleSuccess(BaseResponse<List<BannerData>> response) {
                        if(response.getErrorCode()==0){
                            LogHelper.showLog("广告请求返回="+response.getData().get(0).getTitle());
                            LogHelper.showLog("广告请求返回="+response.getData().get(1).getTitle());
                            LogHelper.showLog("广告请求返回="+response.toString());
                            showShortToast("广告请求成功");
                        }else{
                            showShortToast(response.getErrorMsg());
                        }
                    }
                });
    }


    /**POST请求示例*/
    public void postRegisterClick(View view){
        HttpHelper.getInstance().httpService().getRegisterData("1008623","123456","123456")
                .compose(RxSchedulers.compose())
                .subscribe(new BaseObserver<LoginData>(this){
                    @Override
                    protected void onHandleSuccess(BaseResponse<LoginData> response) {
                        if(response.getErrorCode()==0){
                            showShortToast("注册成功");
                        }else if(response.getErrorCode()==-1){
                            showShortToast("用户名已经被注册");
                        }else{
                            showShortToast("注册失败");
                        }
                        LogHelper.showLog("注册请求返回="+response.toString());
                    }
        });

        /*注释部分是不封装的使用方法*/
//        HttpHelper.getInstance().httpService().getRegisterData("1008623","123456","123456")
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResponse<LoginData>>() {
//                    @Override
//                    public void onError(Throwable e) {
//                        LogHelper.showLog("onError="+e.getMessage());
//                        hideProgressDialog();
//                    }
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        showProgressDialog("加载中...");
//                        LogHelper.showLog("onSubscribe");
//                    }
//                    @Override
//                    public void onComplete() {
//                        LogHelper.showLog("onCompleted");
//                        hideProgressDialog();
//                    }
//                    @Override
//                    public void onNext(BaseResponse<LoginData> result) {
//                        if(result.getErrorCode()==0){
//                            showShortToast("注册成功");
//                        }else if(result.getErrorCode()==-1){
//                            showShortToast("用户名已经被注册");
//                        }else{
//                            showShortToast("注册失败");
//                        }
//                        LogHelper.showLog("注册请求返回="+result.toString());
//                    }
//                });
    }

}
