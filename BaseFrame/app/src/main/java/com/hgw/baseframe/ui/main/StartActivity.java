package com.hgw.baseframe.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.hgw.baseframe.R;
import com.hgw.baseframe.app.BaseFrameApp;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.core.prefs.PreferenceHelper;
import com.hgw.baseframe.util.DirUtil;
import com.hgw.baseframe.util.LogHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 描述：启动页
 * @author hgw
 */

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置窗口全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        //首次安装APP打开，先获取权限
        boolean isFirstStart= PreferenceHelper.getInstance().getIsFirstStart();
        if(isFirstStart){
            getPermission(view);
        }else{
            start(view);
        }

    }

    private void start(View view) {
        //首次打开，才出现启动页
        if(BaseFrameApp.isFirstRun){
            BaseFrameApp.isFirstRun = false;
            startAnimation(view);
        }else{
            toMain();
        }
    }

    public void startAnimation(View view) {
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                //判断是否安装后第一次启动
                boolean isFirstStart= PreferenceHelper.getInstance().getIsFirstStart();
                if(isFirstStart){
                    PreferenceHelper.getInstance().setIsFirstStart(false);
                    toGuide();
                }else{
                    toMain();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    /**跳转介绍页*/
    public void toGuide() {
        GuideActivity.toActivity(this);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**挑战首页*/
    public void toMain() {
        MainActivity.toActivity(this);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**获取权限
     * @param view*/
    private void getPermission(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {

                    //不管权限是否允许都继续启动app
                    start(view);

                    if (permission.granted) {
                        // 用户已经同意该权限（该方法每同意一个权限都会执行一次）
                        //初始化APP目录结构
                        DirUtil.initDirs(this);
                        LogHelper.showLog(permission.name + " is granted.");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        LogHelper.showLog(permission.name + " is denied. More info should be provided.");
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        LogHelper.showLog(permission.name + " is denied.");
                    }
                });
    }
}
