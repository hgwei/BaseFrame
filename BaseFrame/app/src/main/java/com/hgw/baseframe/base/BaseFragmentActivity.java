package com.hgw.baseframe.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hgw.baseframe.view.LoadingProgressDialog;

import java.lang.reflect.Field;

public  class BaseFragmentActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	/**重写getResources()方法，让app页面字体不受系统设置字体大小影响*/
	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config = new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config, res.getDisplayMetrics());
		return res;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	/**显示短时间的Toast*/
	public void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**显示长时间的Toast*/
	public void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/**显示progressDialog*/
	public void showProgressDialog(String message) {
		LoadingProgressDialog.showProgressDialog(this,message);
	}

	/**关闭progressDialog*/
	public void hideProgressDialog() {
		LoadingProgressDialog.hideProgressDialog();
	}
    
	/**
     * 展示软键盘
     */
    public void showKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    /**
	   * 获取版本号
	   * @return 当前应用的版本号
	  */
	 public String getVersionName() {
	      try {
	          PackageManager manager = this.getPackageManager();
	          PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	         return info.versionName;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	     }
	 }
	 
	 /**
	   * 获取版本号(内部识别号)
	   * @return 当前应用的版本号，返回0，代表获取失败
	  */
	 public int getVersionCode() {
	      try {
	          PackageManager manager = this.getPackageManager();
	          PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	         return info.versionCode;
	     } catch (Exception e) {
	         e.printStackTrace();
	         return 0;
	     }
	}

	/** 获取屏幕宽度 */
	public int getWidth() {
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.widthPixels;
	}
	
	/** 获取屏幕高度 */
	public int getHeight() {
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm.heightPixels;
	}

	/** 获取View宽度 */
	public int getViewWidth(View view) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
				, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int countWidth = view.getMeasuredWidth();
		return countWidth;
	}
	
	/** 获取View高度 */
	public int getViewHeight(View view) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
				, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int countHeight = view.getMeasuredHeight();
		return countHeight;
	}
	 
	 /**
	  * 获取状态栏高度
	 */
	 public int getStatusBarHeight(){
			Class<?> c = null;
			Object obj = null;
			Field field = null;
			int x = 0, sbar = 0;
			try {
				c = Class.forName("com.android.internal.R$dimen");
				obj = c.newInstance();
				field = c.getField("status_bar_height");
				x = Integer.parseInt(field.get(obj).toString());
				sbar = this.getResources().getDimensionPixelSize(x);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			return sbar;
	  }
		
		
}
