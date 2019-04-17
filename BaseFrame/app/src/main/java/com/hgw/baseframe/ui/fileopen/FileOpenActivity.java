package com.hgw.baseframe.ui.fileopen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.ui.http.okhttp.OkhttpActivity;
import com.hgw.baseframe.ui.http.retrofit.RetrofitActivity;

/**
 * 描述：文件在线预览
 * @author hgw
 * */
public class FileOpenActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, FileOpenActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_open);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.openWPS).setOnClickListener(this);
        findViewById(R.id.openTBS).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.openWPS:
                /**调用WPS打开*/
                OkhttpActivity.toActivity(this);
                break;
            case R.id.openTBS:
                /**集成TBS*/
                RetrofitActivity.toActivity(this);
                break;
            default:
                break;
        }
    }

}
