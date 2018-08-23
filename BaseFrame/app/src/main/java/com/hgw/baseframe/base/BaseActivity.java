package com.hgw.baseframe.base;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.hgw.baseframe.view.LoadingProgressDialog;

/**
 * 描述：AppCompatActivity基类
 * @author hgw
 * */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置系统状态栏背景颜色
        //SystemBarUtil.setSystemBarColor(this,getResources().getColor(R.color.titleBar));
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**重写getResources()方法，让app页面字体不受系统设置字体大小影响*/
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**显示短时间的Toast*/
    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**显示长时间的Toast*/
    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**重写onDestroy方法*/
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    /**显示progressDialog*/
    public void showProgressDialog(String message) {
        LoadingProgressDialog.showProgressDialog(this,message);
    }

    /**关闭progressDialog*/
    public void hideProgressDialog() {
        LoadingProgressDialog.hideProgressDialog();
    }

    /**
     * 展示软键盘
     */
    public void showKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
