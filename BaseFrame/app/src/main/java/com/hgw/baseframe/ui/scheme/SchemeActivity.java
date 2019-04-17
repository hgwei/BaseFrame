package com.hgw.baseframe.ui.scheme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;

/**
 * 描述：Scheme使用（H5或其它APP调起本APP）
 * @author hgw
 *
 * 备注：
 * Scheme使用:https://blog.csdn.net/sinat_31057219/article/details/78362326
 *
 * 使用步骤：
 * 1、AndroidManifest.xml配置Scheme协议，参考AndroidManifest.xml启动页Activity配置
 * 2、调用：
 *  （1）网页调用：<a href="scheme://com.hgw.baseframe/toSchemeActivity?testId=123456">启动baseFrame应用并传参</a>
 *  （2）原生调用：
 *  Intent intent = new Intent(Intent.ACTION_VIEW,
 *  Uri.parse("scheme://com.hgw.baseframe/toSchemeActivity?testId=123456"));
 *  startActivity(intent);
 *  3、监听被调用事件，获取Scheme跳转的参数，参考StartActivity.java文件
 * */
public class SchemeActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, SchemeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
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
