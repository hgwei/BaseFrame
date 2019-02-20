package com.hgw.baseframe.ui.main;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hgw.baseframe.BuildConfig;
import com.hgw.baseframe.R;
import com.hgw.baseframe.app.BaseFrameApp;
import com.hgw.baseframe.base.BaseFragment;
import com.hgw.baseframe.bean.LoginEntry;
import com.hgw.baseframe.service.FileDownLoadService;
import com.hgw.baseframe.ui.login.LoginActivity;
import com.hgw.baseframe.util.DirUtil;
import com.hgw.baseframe.util.LogHelper;
import com.hgw.baseframe.util.MethodCommon;
import com.hgw.baseframe.util.OpenFileUtil;
import com.hgw.baseframe.view.CommonDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

/**
 * 描述：个人中心 Fragment
 * @author hgw
 * */
public final class PersonFragment extends BaseFragment implements View.OnClickListener{
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_person, container,false);
		
		initView(view);
		return view;
	}

	private void initView(View view) {
    	view.findViewById(R.id.logoutRL).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.logoutRL:
				/**退出*/
				showLogoutTipDialod();
				break;
			default:
				break;
		}
	}

	/**
	 * 弹出注销提示 Dialog
	 */
	private void showLogoutTipDialod() {
		CommonDialog tipsDialog = new CommonDialog(getActivity());
		tipsDialog.setCancelable(false);
		tipsDialog.setCanceledOnTouchOutside(false);
		tipsDialog.setContent("确认退出当前账号？");

		tipsDialog.setPositiveButton("确认", new CommonDialog.CommonDialogInterface() {
			@Override
			public boolean onClick() {
				LoginEntry.Instance().logout();
				//清空历史栈，跳转登录页面
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				getActivity().finish();
				return true;
			}
		});
		tipsDialog.setNegativeButton("取消", new CommonDialog.CommonDialogInterface() {
			@Override
			public boolean onClick() {
				return true;
			}
		});
		tipsDialog.show();
	}

}
