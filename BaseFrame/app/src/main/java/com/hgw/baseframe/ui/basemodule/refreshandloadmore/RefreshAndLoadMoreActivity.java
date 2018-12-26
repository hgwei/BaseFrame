package com.hgw.baseframe.ui.basemodule.refreshandloadmore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.ui.basemodule.refreshandloadmore.xrecyclerview.CustomXRecyclerViewActivity;

/**
 * 描述：刷新、加载更多框架
 * @author hgw
 * */
public class RefreshAndLoadMoreActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, RefreshAndLoadMoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshandloadmore);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.xRecyclerView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.xRecyclerView:
                /**XRecyclerView*/
                CustomXRecyclerViewActivity.toActivity(this);
                break;
            default:
                break;
        }
    }

}
