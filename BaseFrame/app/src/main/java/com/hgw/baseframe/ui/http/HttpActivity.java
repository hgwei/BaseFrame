package com.hgw.baseframe.ui.http;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.ui.http.okhttp.OkhttpActivity;
import com.hgw.baseframe.ui.http.retrofit.RetrofitActivity;

/**
 * 描述：Http
 * @author hgw
 * */
public class HttpActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, HttpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.okhttp).setOnClickListener(this);
        findViewById(R.id.retrofit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.okhttp:
                /**okhttp*/
                OkhttpActivity.toActivity(this);
                break;
            case R.id.retrofit:
                /**retrofit*/
                RetrofitActivity.toActivity(this);
                break;
            default:
                break;
        }
    }

}
