package com.hgw.baseframe.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.ui.BaseActivity;
import com.hgw.baseframe.ui.main.contract.SplashContract;
import com.hgw.baseframe.ui.main.presenter.SplashPresenter;

/**
 * 描述：启动页
 * @author hgw
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @Override
    protected void initPresenter() {
        mPresenter=new SplashPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置窗口全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        mPresenter.start(view);
    }

    @Override
    public void toSplash() {
        GuideActivity.toActivity(this);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void toMain() {
        MainActivity.toActivity(this);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
