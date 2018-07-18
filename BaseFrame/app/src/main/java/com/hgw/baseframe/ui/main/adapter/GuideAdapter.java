package com.hgw.baseframe.ui.main.adapter;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 描述:APP引导页--适配器
 * @author hgw
 * */
public class GuideAdapter extends PagerAdapter {
	/**界面列表*/
    private ArrayList<View> views;
    
    public GuideAdapter(ArrayList<View> views){
        this.views = views;
    }
       
	/**
	 * 获得当前界面数
	 */
	@Override
	public int getCount() {
		 if (views != null) {
             return views.size();
         }      
         return 0;
	}

	/**
	 * 初始化position位置的界面
	 */
    @Override
    public Object instantiateItem(View view, int position) {
       
        ((ViewPager) view).addView(views.get(position), 0);
       
        return views.get(position);
    }
    
    /**
	 * 判断是否由对象生成界面
	 */
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

	/**
	 * 销毁position位置的界面
	 */
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager) view).removeView(views.get(position));       
    }
}
