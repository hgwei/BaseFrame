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
	//文件下载dialog
	private AlertDialog mVersionLoadingDialog;
	//文件下载dialog中的文本
	private TextView mmessage;
	//文件下载dialog中的下载进度条
	private ProgressBar mProgressBar;
	/**下载文件名、下载地址*/
	private String fileName,downloadURL;
	/**APK下载后的路径*/
	private String fileLocalPath;
	private static final int INSTALL_PACKAGES_REQUESTCODE=106;
	private static final int GET_UNKNOWN_APP_SOURCES=107;

    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_person, container,false);
		
		initView(view);
		return view;
	}

	private void initView(View view) {
		view.findViewById(R.id.updateRL).setOnClickListener(this);
    	view.findViewById(R.id.logoutRL).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.updateRL:
				/**版本更新*/
				checkUpdate();
				break;
			case R.id.logoutRL:
				/**退出*/
				showLogoutTipDialod();
				break;
			default:
				break;
		}
	}

	/**检查更新*/
	private void checkUpdate() {
		//这里使用模拟数据（实际项目中，这里的数据来源是服务端接口）
		String versionCode="2"; //版本号
		String updateMessage="更新内容：\n 1、增加新功能；\n 2、修复一些问题。"; //更新内容
		fileName="新版BaseFrame.apk"; //下载文件名
		downloadURL="http://app-global.pgyer.com/29dca5fe205fb308fda92b9d4a6750ac.apk?attname=app-debug.apk&sign=e6f283dba7e885dd4d750a3665ddff08&t=5c6a85c1"; //apk下载地址

		if(Double.valueOf(versionCode)> MethodCommon.getVersionCode(getActivity())){
			showUpdateDialog(updateMessage);
		}else{
			showShortToast("当前已是最新版本！");
		}
	}

	/**
	 * 弹出版本更新提示 Dialog
	 * @param updateMessage 更新内容
	 * */
	private void showUpdateDialog(String updateMessage) {
		final AlertDialog mDialog = new AlertDialog.Builder(getActivity()).create();
		mDialog.setCanceledOnTouchOutside(true);
		mDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);	//弹出窗位置
		mDialog.show();

		LayoutInflater mInflater = this.getLayoutInflater();
		final View view = mInflater.inflate(R.layout.dialog_version_update_tip, null);
		mDialog.getWindow().setContentView(view);
		mDialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);	//布局大小
		mDialog.getWindow().setWindowAnimations(R.style.Animation_Dialog_center_enter_exit);	//动画效果，没动画就注释掉

		TextView mSure=(TextView) view.findViewById(R.id.sure);
		TextView mCancel=(TextView) view.findViewById(R.id.cancel);
		TextView mmessage=(TextView) view.findViewById(R.id.message);
		mmessage.setText(updateMessage);
		mSure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getPermission();
				mDialog.dismiss();
			}
		});

		mCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
	}

	/**
	 * 获取权限
	 * 注意：请求多少个权限，结果就会回调多少次，所以要加个布尔值，防止重复操作
	 * */
	private boolean isFirstPermission=true;
	private void getPermission() {
		RxPermissions rxPermissions = new RxPermissions(this);
		rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribe(permission -> {
					if (permission.granted) {
						// 用户已经同意该权限（该方法每同意一个权限都会执行一次）
						if(isFirstPermission){
							isFirstPermission=false;
							//下载APK
							startDownFile();
						}
						LogHelper.showLog(permission.name + " is granted.");
					} else if (permission.shouldShowRequestPermissionRationale) {
						// 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
						LogHelper.showLog(permission.name + " is denied. More info should be provided.");
					} else {
						// 用户拒绝了该权限，并且选中『不再询问』
						LogHelper.showLog(permission.name + " is denied.");
					}
				});
	}

	/**
	 * 开启服务去下载文件并打开
	 */
	public void startDownFile() {
		// 启动Service 开始下载
		Intent intent = new Intent(getActivity(), FileDownLoadService.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("fileName", fileName); //文件名
		intent.putExtra("downloadURL", downloadURL); //文件下载地址
		getActivity().startService(intent);

		//弹出apk下载更新 Dialog
		showApkLoadingDialog();

		//apk下载监听
		FileDownLoadService.setFileListener(new FileDownLoadService.FileListener() {
			@Override
			public void onFail() {
				Log.e("Test", "apk下载失败");
			}

			@Override
			public void onSuccess(String response) {
				Log.e("Test", "apk下载成功");
				fileLocalPath=response;
				//下载完成，关闭版本更新dialog
				mVersionLoadingDialog.dismiss();
				checkIsAndroidO();
			}

			@Override
			public void onProgress(int progress, long total) {
				//progress：下载进度；total：文件总大小
				Log.e("Test", "下载进度progress=" + progress);

				//更新版本更新dialog提示内容
				mmessage.setText("正在下载中：" + progress + "%");
				mProgressBar.setProgress(progress);
			}
		});
	}

	/**
	 * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
	 */
	private void checkIsAndroidO() {
		LogHelper.showLog("..........................checkIsAndroidO");
		if (Build.VERSION.SDK_INT >= 26) {
			boolean b = getActivity().getPackageManager().canRequestPackageInstalls();
			if (b) {
				installApk();//安装应用的逻辑(写自己的就可以)
			} else {
				//请求安装未知应用来源的权限
				LogHelper.showLog("..........................安装未知应用来源的权限");
				//请求权限Fragment使用的方法
				requestPermissions(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
				//请求权限Activity使用的方法
				//ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
			}
		} else {
			installApk();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case INSTALL_PACKAGES_REQUESTCODE:
				LogHelper.showLog("..........................安装未知应用来源的权限onRequestPermissionsResult");
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					installApk();
				} else {
					Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
					Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
					startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
				}
				break;
		}
	}

	/**
	 * 接收回调
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case GET_UNKNOWN_APP_SOURCES:
				/**允许安装未知文件回调*/
				LogHelper.showLog("..........................允许安装未知文件回调");
				installApk();
				break;
		}
	}

	/**
	 * 安装apk文件
	 * @return
	 */
	private void installApk() {
		LogHelper.showLog("fileLocalPath="+fileLocalPath);
		File apkFile = new File(fileLocalPath);

		Intent install = new Intent(Intent.ACTION_VIEW);
		install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//版本是否在Android7.0以上
		if (Build.VERSION.SDK_INT >= 24) {
			install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
			install.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		}
		startActivity(install);
	}

	/**
	 * 弹出apk下载更新 Dialog
	 */
	private void showApkLoadingDialog() {
		mVersionLoadingDialog = new AlertDialog.Builder(getActivity()).create();
		mVersionLoadingDialog.setCanceledOnTouchOutside(false);
		mVersionLoadingDialog.setCancelable(false);
		mVersionLoadingDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);    //弹出窗位置
		mVersionLoadingDialog.show();

		LayoutInflater mInflater = this.getLayoutInflater();
		final View view = mInflater.inflate(R.layout.dialog_file_loading, null);
		mVersionLoadingDialog.getWindow().setContentView(view);
		mVersionLoadingDialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);    //布局大小
		mVersionLoadingDialog.getWindow().setWindowAnimations(R.style.Animation_Dialog_center_enter_exit);    //动画效果，没动画就注释掉

		TextView mCancel = (TextView) view.findViewById(R.id.cancel);
		TextView mSure = (TextView) view.findViewById(R.id.sure);
		mmessage = (TextView) view.findViewById(R.id.message);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		mCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//取消下载（停止文件下载服务）
				Intent intent = new Intent(getActivity(), FileDownLoadService.class);
				getActivity().stopService(intent);
				mVersionLoadingDialog.dismiss();
			}
		});
		mSure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//后台下载
				mVersionLoadingDialog.dismiss();
			}
		});
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
