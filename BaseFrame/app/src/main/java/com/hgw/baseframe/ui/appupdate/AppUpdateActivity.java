package com.hgw.baseframe.ui.appupdate;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hgw.baseframe.BuildConfig;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.service.FileDownLoadService;
import com.hgw.baseframe.util.LogHelper;
import com.hgw.baseframe.util.MethodCommon;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

/**
 * 描述：APP版本检测更新（因为要适配Android 8.0，所以该升级操作需要放在MainActivity中进行APK的安装）
 * @author hgw
 * */
public class AppUpdateActivity extends BaseActivity implements View.OnClickListener {
    private static final int INSTALL_PACKAGES_REQUESTCODE=106;
    private static final int GET_UNKNOWN_APP_SOURCES=107;
    /**文件下载dialog*/
    private AlertDialog mVersionLoadingDialog;
    /**文件下载dialog中的文本*/
    private TextView mmessage;
    /**文件下载dialog中的下载进度条*/
    private ProgressBar mProgressBar;
    /**下载文件名、下载地址*/
    private String fileName,downloadURL;
    /**APK下载后的路径*/
    private String fileLocalPath;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, AppUpdateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appupdate);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.checkUpdate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.checkUpdate:
                /**APP版本检测更新*/
                checkUpdate();
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
        downloadURL="http://d3g.qq.com/sngapp/app/update/20170427151125_2027/qzone_7.2.5.291_android_r167662_2017-04-25_16-09-42_QZGW_D.apk"; //apk下载地址

        if(Double.valueOf(versionCode)> MethodCommon.getVersionCode(this)){
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
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
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
        Intent intent = new Intent(this, AppUpdateService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fileName", fileName); //文件名
        intent.putExtra("downloadURL", downloadURL); //文件下载地址
        startService(intent);

        //弹出apk下载更新 Dialog
        showApkLoadingDialog();

        //apk下载监听
        AppUpdateService.setFileListener(new AppUpdateService.FileListener() {
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
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = this.getPackageManager().canRequestPackageInstalls();
            if (b) {
                installApk();//安装应用的逻辑(写自己的就可以)
            } else {
                //请求安装未知应用来源的权限
                //Fragment使用的方法
                //requestPermissions(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
                //Activity使用的方法
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installApk();
                } else {
                    Uri packageURI = Uri.parse("package:" + this.getPackageName());
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
                /**允许安装未知来源文件权限后回调*/
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
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
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
        mVersionLoadingDialog = new AlertDialog.Builder(this).create();
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
                Intent intent = new Intent(AppUpdateActivity.this, FileDownLoadService.class);
                AppUpdateActivity.this.stopService(intent);
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

}
