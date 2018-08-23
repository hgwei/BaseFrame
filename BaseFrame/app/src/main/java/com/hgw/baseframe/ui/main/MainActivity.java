package com.hgw.baseframe.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.adapter.MainAdapter;

/**
 * 描述：MainActivity
 * @author hgw
 * */

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.tab_know:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.tab_navigation:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.tab_project:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.tab_project2:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());

        adapter.addFragment(HomeFragment.getInstance(true,"1"));
        adapter.addFragment(HomeFragment.getInstance(true,"2"));
        adapter.addFragment(HomeFragment.getInstance(true,"3"));
        adapter.addFragment(HomeFragment.getInstance(true,"4"));
        adapter.addFragment(HomeFragment.getInstance(true,"5"));
        viewPager.setAdapter(adapter);
    }
}
