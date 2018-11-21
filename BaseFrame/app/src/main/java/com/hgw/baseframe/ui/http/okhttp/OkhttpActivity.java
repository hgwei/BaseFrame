package com.hgw.baseframe.ui.http.okhttp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;

/**
 * 描述：Okhttp
 * @author hgw
 * */
public class OkhttpActivity extends BaseActivity implements View.OnClickListener {

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
    }

    /**Get请求示例*/
    public void getClick(View view){

    }


    /**POST请求示例*/
    public void postClick(View view){

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
