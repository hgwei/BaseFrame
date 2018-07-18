package com.hgw.baseframe.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.ui.BaseActivity;
import com.hgw.baseframe.ui.main.adapter.GuideAdapter;
import com.hgw.baseframe.ui.main.contract.GuideContract;
import com.hgw.baseframe.ui.main.presenter.GuidePresenter;

import java.util.ArrayList;

/**
 * 描述：引导介绍页
 * @author hgw
 */
public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View  {
    /**ViewPager*/
    private ViewPager viewPager;
    /**iewPager适配器*/
    private GuideAdapter vpAdapter;
    /**ArrayList来存放View*/
    private ArrayList<View> views;

    @Override
    protected void initPresenter() {
        mPresenter=new GuidePresenter();
    }

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);

        views = new ArrayList<>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.activity_guide_view1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.activity_guide_view2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.activity_guide_view3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.activity_guide_view4, null);


        view4.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
            }
        });

        //将要分页显示的View装入数组中
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        vpAdapter = new GuideAdapter(views);
        viewPager.setAdapter(vpAdapter);
    }

    public void toMain() {
        MainActivity.toActivity(this);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
