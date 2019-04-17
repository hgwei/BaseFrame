package com.hgw.baseframe.ui.http.okhttp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.bean.login.LoginBean;
import com.hgw.baseframe.bean.main.BannerBean;
import com.hgw.baseframe.constants.RequestData;
import com.hgw.baseframe.constants.ResultCode;
import com.hgw.baseframe.constants.UrlConstants;
import com.hgw.baseframe.core.http.RequestGet;
import com.hgw.baseframe.core.http.RequestPost;
import com.hgw.baseframe.core.http.RequestPostJson;
import com.hgw.baseframe.util.FastJsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 描述：Okhttp（http、https）
 * @author hgw
 *
 * 本框架的封装来源：https://github.com/hongyangAndroid/okhttputils
 * */
public class OkhttpActivity extends BaseActivity implements View.OnClickListener {
    /**resultText*/
    private TextView mresultText;
    /**progressBar*/
    private ProgressBar mprogressBar;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, OkhttpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        mresultText= findViewById(R.id.resultText);
        mprogressBar= findViewById(R.id.progressBar);
    }

    /**Get请求示例*/
    public void getClick(View view){
        showProgressDialog(null);
        String url="http://www.wanandroid.com/banner/json";
        new RequestGet(this, url)
                .setRequestCallback(new RequestGet.RequestCallback() {
                    @Override
                    public void onSuccess(String response) {
                        hideProgressDialog();
                        BannerBean mResult = FastJsonUtil.parseToJavaBean(response, BannerBean.class);
                        if (mResult.getErrorCode()== ResultCode.RESULT_OK){
                            /*
                            * 处理业务
                            */
                            mresultText.setText("请求成功="+response);
                        }else{
                            showShortToast(mResult.getErrorMsg());
                        }
                    }
                    @Override
                    public void onError(String response) {
                        hideProgressDialog();
                    }
                });
    }


    /**POST请求示例*/
    public void postClick(View view){
        showProgressDialog(null);
        Map<String, String> params= RequestData.getLogin("1008611","123456");
        new RequestPost(this, UrlConstants.URL_USERLOGIN,params)
                .setRequestCallback(new RequestPost.RequestCallback() {
                    @Override
                    public void onSuccess(String response) {
                        hideProgressDialog();
                        LoginBean mResult = FastJsonUtil.parseToJavaBean(response, LoginBean.class);
                        if (mResult.getErrorCode()== ResultCode.RESULT_OK){
                            /*
                             * 处理业务
                             */
                            mresultText.setText("请求成功="+response);
                        }else{
                            showShortToast(mResult.getErrorMsg());
                        }
                    }
                    @Override
                    public void onError(String response) {
                        hideProgressDialog();
                    }
                });
    }

    /**POST String请求示例*/
    public void postStringClick(View view){
        showProgressDialog(null);
        String params= RequestData.getLogin2("1008611","123456");
        new RequestPostJson(this, UrlConstants.URL_USERLOGIN,params)
                .setRequestCallback(new RequestPostJson.RequestCallback() {
                    @Override
                    public void onSuccess(String response) {
                        hideProgressDialog();
                        LoginBean mResult = FastJsonUtil.parseToJavaBean(response, LoginBean.class);
                        if (mResult.getErrorCode()== ResultCode.RESULT_OK){
                            /*
                             * 处理业务
                             */
                            mresultText.setText("请求成功="+response);
                        }else{
                            showShortToast(mResult.getErrorMsg());
                        }
                    }
                    @Override
                    public void onError(String response) {
                        hideProgressDialog();
                    }
                });
    }

    /**加载网络图片到view中*/
    public void getImageToViewClick(View view) {
        String url = "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";
        OkHttpUtils.get()
                .url(url)
                .tag(this)
                .build()
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        ImageView mImageView=findViewById(R.id.testImage);
                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }

    /**上传单个文件*/
    public void uploadFile(View view) {
        String url = "http://openapi.huamalong.com:8899/mobile/index.php?app=mobile&controller=member&action=register";
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        //参数
        Map<String, String> params = new HashMap<>();
        params.put("username", "测试");
        params.put("password", "123456");
        //头部
        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        //mFile：接口请求参数为文件的key；"messenger_01.png"：文件名称；file：文件
        OkHttpUtils.post()
                .addFile("mFile", "messenger_01.png", file)
                .url(url)
                .params(params)
                .headers(headers)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mresultText.setText("请求失败="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mresultText.setText("请求成功="+response);
                    }
                });
    }

    /**上传多个文件*/
    public void multiFileUpload(View view) {
        String url = "http://openapi.huamalong.com:8899/mobile/index.php?app=mobile&controller=member&action=register";
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1.txt");
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "测试");
        params.put("password", "123456");

        OkHttpUtils.post()
                .addFile("mFile", "messenger_01.png", file)
                .addFile("mFile", "test1.txt", file2)
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mresultText.setText("请求失败="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mresultText.setText("请求成功="+response);
                    }
                });
    }

    /**
     * 下载文件
     * （保存文件到内部存储卡需要先申请权限）
     * */
    public void downloadFile(View view) {
        String url = "http://d3g.qq.com/sngapp/app/update/20170427151125_2027/qzone_7.2.5.291_android_r167662_2017-04-25_16-09-42_QZGW_D.apk";
        OkHttpUtils.get()
                .url(url)
                .build()
                //注意：下载文件可以使用FileCallback，需要传入文件保存路径、文件名
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "QQ空间.apk"){
                    @Override
                    public void onBefore(Request request, int id) {
                        System.out.println("下载开始......");
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        System.out.println("下载完成......");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mprogressBar.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mresultText.setText("请求失败="+e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        mresultText.setText("文件下载成功="+ file.getAbsolutePath());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            default:
                break;
        }
    }

}
