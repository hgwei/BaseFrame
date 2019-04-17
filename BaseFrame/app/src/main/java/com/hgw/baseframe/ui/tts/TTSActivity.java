package com.hgw.baseframe.ui.tts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.util.LogHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 描述：离线语音合成（云知声）
 * @author hgw
 *
 * libs目录下：libuscasr.so、libyzstts.so、usc.jar
 * assets目录下：backend_lzl、frontend_model
 * AndroidManifest.xml权限
 * */
public class TTSActivity extends BaseActivity implements View.OnClickListener {
    private EditText minput;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, TTSActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        initView();
        getPermission();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.compose).setOnClickListener(this);
        minput=findViewById(R.id.input);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.compose:
                /**离线语音合成*/
                TTSUtils.getInstance().speak(minput.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 主动停止识别
        TTSUtils.getInstance().pause();
    }

    public void onDestroy() {
        // 主动释放离线引擎
        TTSUtils.getInstance().release();
        super.onDestroy();
    }

    /**
     * 获取权限
     * 注意：请求多少个权限，结果就会回调多少次，所以要加个布尔值，防止重复操作
     * */
    private boolean isFirstPermission=true;
    private void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限（该方法每同意一个权限都会执行一次）
                        if(isFirstPermission){
                            isFirstPermission=false;
                            //云知声SDK初始化
                            initTTS();
                        }
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

    /**云知声语音合成SDK初始化*/
    private void initTTS() {
        TTSUtils.getInstance().init();
    }

}
