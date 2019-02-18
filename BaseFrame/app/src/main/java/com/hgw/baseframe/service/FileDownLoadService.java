package com.hgw.baseframe.service;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.hgw.baseframe.BuildConfig;
import com.hgw.baseframe.R;
import com.hgw.baseframe.app.BaseFrameApp;
import com.hgw.baseframe.util.DirUtil;
import com.hgw.baseframe.util.LogHelper;
import com.hgw.baseframe.util.OpenFileUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.File;
import okhttp3.Call;

/**
 * 描述：文件下载预览服务（调用WPS应用打开，或者调用系统应用打开）
 * @author hgw
 * */
public class FileDownLoadService extends Service {
    private String mDownloadUrl;//文件的下载路径
    private String downloadPathDIR= DirUtil.PATH_DOWNLOAD;//文件下载完后保存的目录
    private String FileName;//文件名称
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    //上一次下载进度
    private int lastProgress=0;
    public static final String CHANNEL_ID = "default";
    private static final String CHANNEL_NAME = "Default Channel";

    /**文件下载接口*/
    private static FileListener mFileListener;
    /**注册文件下载接口*/
    public static void setFileListener(FileListener listener) {
        mFileListener = listener;
    }
    /**定义文件下载接口*/
    public interface FileListener {
        void onFail();    //下载失败
        void onSuccess(String response); //下载成功
        void onProgress(int progress, long total); //下载进度（progress：下载进度；total：文件总大小）
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            notifyMsg("文件下载", "文件下载失败", 0);
            stopSelf();
        }
        FileName =intent.getExtras().getString("fileName", ""); //文件名
        mDownloadUrl = intent.getExtras().getString("downloadURL", "");//文件的下载路径
        //下载文件
        downloadFile(mDownloadUrl);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭下载进度通知栏
        mNotificationManager.cancel(0);
        //取消标签为"downApk"的下载请求
        OkHttpUtils.getInstance().cancelTag("downFile");
    }

    /**
     * 下载文件
     * @param url
     */
    private void downloadFile(String url) {
        LogHelper.showLog("开始下载文件："+url);
        OkHttpUtils.get().tag("downFile").url(url).build().execute(new FileCallBack(downloadPathDIR, FileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                notifyMsg("文件下载", "文件下载失败", 0);
                //下载失败接口回调
                if(mFileListener!=null){
                    mFileListener.onFail();
                }
                stopSelf();
            }

            @Override
            public void onResponse(File response, int id) {
                //下载成功接口回调
                if(mFileListener!=null){
                    String filePath=downloadPathDIR + FileName;
                    mFileListener.onSuccess(filePath);
                }

                mNotificationManager.cancel(0); //关闭下载进度通知栏
                openFile();
                stopSelf();
            }

            @Override
            public void inProgress(final float progress, long total, int id) {
                Log.e("Test","total="+total);
                //progress*100为当前文件下载进度，total为文件大小
                int newProgress=(int) (progress * 100);
                if(newProgress>lastProgress){
                    lastProgress=newProgress;
                    notifyMsg("文件下载", "文件正在下载："+newProgress+"%", newProgress);
                    //加载进度接口回调
                    if(mFileListener!=null){
                        mFileListener.onProgress(newProgress,total);
                    }
                }
            }
        });
    }

    /**
     * 设置NotificationChannel（适配Android8.0以上版本）
     * */
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        //是否绕过请勿打扰模式
        channel.canBypassDnd();
        //闪光灯
        channel.enableLights(true);
        //锁屏显示通知
        channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET);
        //闪关灯的灯光颜色
        channel.setLightColor(Color.RED);
        //桌面launcher的消息角标
        channel.canShowBadge();
        //是否允许震动
        channel.enableVibration(true);
        //获取系统通知响铃声音的配置
        channel.getAudioAttributes();
        //获取通知取到组
        channel.getGroup();
        //设置可绕过  请勿打扰模式
        channel.setBypassDnd(true);
        //设置震动模式
        channel.setVibrationPattern(new long[]{100, 100, 200});
        //是否会有灯光
        channel.shouldShowLights();
        mNotificationManager.createNotificationChannel(channel);
    }

    /**
     * 显示下载进度通知栏
     * @param title 标题
     * @param content 文本
     * @param progress 进度
     * */
    private void notifyMsg(String title, String content, int progress) {
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");//为了向下兼容，这里采用了v7包下的NotificationCompat来构造
        builder.setSmallIcon(R.mipmap.ic_launcher).
                setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher)).setContentTitle(title);
        if (progress > 0 && progress <= 100) {
            //下载进行中
            builder.setProgress(100, progress, false);
        } else {
            builder.setProgress(0, 0, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentText(content);
        mNotification = builder.build();
        mNotificationManager.notify(0, mNotification);
    }

    /**
     * 调用系统应用打开文件
     */
    private void openFile() {
        String filePath=downloadPathDIR + FileName;
        LogHelper.showLog("文件地址="+filePath);
//        if(checkWps()){
//            //使用WPS打开文件
//            openWithWps(filePath);
//        }else{
//            //使用系统应用打开文件
//            OpenFileUtil.getInstance(this).openFile(filePath);
//        }
    }

    /**检查是否已安装WPS*/
    private boolean checkWps(){
        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.wps.moffice_eng");//WPS个人版的包名
        if (intent == null) {
            return false;
        } else {
            return true;
        }
    }

    /**调用WPS打开应用*/
    public void openWithWps(String fileUrl){
        File file = new File(fileUrl);

        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        Intent intent = this.getPackageManager().getLaunchIntentForPackage( "cn.wps.moffice_eng");
        Bundle bundle = new Bundle();
        intent.setData(Uri.fromFile(file));//这里采用传入文档的在线地址进行打开，免除下载的步骤，也不需要判断安卓版本号
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    /**
     * 安装apk文件
     * @return
     */
    private void InstallAPK() {
        File apkFile = new File(downloadPathDIR + FileName);

        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        Intent install = new Intent(Intent.ACTION_VIEW);
        //版本是否在Android7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            install.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(install);

        //下面这个方法也可以
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()), "application/vnd.android.package-archive");
        startActivity(intent);*/
    }

}
