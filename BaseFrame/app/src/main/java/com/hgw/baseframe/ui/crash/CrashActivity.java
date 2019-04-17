package com.hgw.baseframe.ui.crash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;

/**
 * 描述：APP异常日志捕获、重启应用
 * @author hgw
 * */
public class CrashActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, CrashActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.crash).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.crash:
                /**触发异常*/
                // 动手制造异常
                String str = null;
                String[] split = str.split(",");
                break;
            default:
                break;
        }
    }

}
