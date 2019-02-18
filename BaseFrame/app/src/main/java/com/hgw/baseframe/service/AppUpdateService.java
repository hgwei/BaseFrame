package com.hgw.baseframe.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.hgw.baseframe.BuildConfig;
import com.hgw.baseframe.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import java.io.File;
import okhttp3.Call;

/**
 * 描述：APP更新
 * @author hgw
 * */
public class AppUpdateService extends Service {
    private String mDownloadUrl;//APK的下载路径
    private String downloadPathDIR;//APK下载完后保存的目录
    private String FileName="BaseDemo.apk";//APK名称
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    //上一次下载进度
    private int lastProgress=0;

    /**apk下载接口*/
    private static APPUpdateListener mAPPUpdateListener;
    /**注册apk下载接口*/
    public static void setAPPUpdateListener(APPUpdateListener listener) {
        mAPPUpdateListener = listener;
    }
    /**定义apk下载接口*/
    public interface APPUpdateListener {
        void onFail();    //下载失败
        void onSuccess(); //下载成功
        void onProgress(int progress, long total); //下载进度（progress：下载进度；total：文件总大小）
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            notifyMsg("版本更新", "文件下载失败", 0);
            stopSelf();
        }
        mDownloadUrl = intent.getExtras().getString("apkUrl", "");//APK的下载路径
        downloadPathDIR =intent.getExtras().getString("downloadPathDIR", ""); //APK下载完后保存的目录
        //下载APK
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
        OkHttpUtils.getInstance().cancelTag("downApk");
    }

    /**
     * 下载apk文件
     * @param url
     */
    private void downloadFile(String url) {
        OkHttpUtils.get().tag("downApk").url(url).build().execute(new FileCallBack(downloadPathDIR, FileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                notifyMsg("版本更新", "文件下载失败", 0);
                //下载失败接口回调
                if(mAPPUpdateListener!=null){
                    mAPPUpdateListener.onFail();
                }
                stopSelf();
            }

            @Override
            public void onResponse(File response, int id) {
                //下载成功接口回调
                if(mAPPUpdateListener!=null){
                    mAPPUpdateListener.onSuccess();
                }

                mNotificationManager.cancel(0); //关闭下载进度通知栏
                InstallAPK();
                stopSelf();
            }

            @Override
            public void inProgress(final float progress, long total, int id) {
                Log.e("Test","total="+total);
                //progress*100为当前文件下载进度，total为文件大小
                int newProgress=(int) (progress * 100);
                if(newProgress>lastProgress){
                    lastProgress=newProgress;
                    notifyMsg("版本更新", "文件正在下载："+newProgress+"%", newProgress);
                    //加载进度接口回调
                    if(mAPPUpdateListener!=null){
                        mAPPUpdateListener.onProgress(newProgress,total);
                    }
                }
            }
        });
    }

    /**
     * 显示下载进度通知栏
     * @param title 标题
     * @param content 文本
     * @param progress 进度
     * */
    private void notifyMsg(String title, String content, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);//为了向下兼容，这里采用了v7包下的NotificationCompat来构造
        builder.setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(),
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
     * 安装apk文件
     * @return
     */
    private void InstallAPK() {
        File apkFile = new File(downloadPathDIR + FileName);

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

//    /*** 下面部分代码是使用HttpURLConnection方法下载，不依赖okhttp（可以代替downloadFile()，功能一样） start ***/
//    /**
//     * 下载apk文件（HttpURLConnection方法，不依赖okhttp）
//     * @param apkUrl
//     */
//    private void downloadFile2(final String apkUrl) {
//        Thread mThread=new Thread(){
//            public void run(){
//                try {
//                    //构建URL地址
//                    URL url = new URL(apkUrl);
//                    //打开打开打开
//                    HttpURLConnection hcont = (HttpURLConnection) url.openConnection();
//                    //建立实际链接
//                    hcont.connect();
//                    //获取输入流内容
//                    InputStream is = hcont.getInputStream();
//                    //获取文件总大小
//                    long totalSize = hcont.getContentLength();
//                    //文件下载保存地址
//                    String path = downloadPathDIR + FileName;
//                    //写入文件
//                    OutputStream os = new FileOutputStream(path);
//                    int length;
//                    long currentSize = 0; //文件当前文件下载完成大小
//                    byte[] bytes = new byte[1024];
//                    while ((length = is.read(bytes)) != -1) {
//                        os.write(bytes, 0, length);
//                        //获取当前进度值
//                        currentSize += length;
//
//                        //更新下载进度
//                        int progress = (int) (currentSize * 100 / totalSize);
//                        //发送消息给handler
//                        Message message = Message.obtain();
//                        message.what = progress; //下载进度
//                        message.obj = totalSize + ""; //文件总大小
//                        handler.sendMessage(message);
//                    }
//                    //关闭流
//                    is.close();
//                    os.close();
//                    os.flush();
//
//                    //下载完成后，发送信息给主线程
//                    Message message = Message.obtain();
//                    message.what = 100; //下载进度
//                    message.obj = totalSize + ""; //文件总大小
//                    handler.sendMessage(message);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    //下载失败接口回调
//                    if(mAPPUpdateListener!=null){
//                        mAPPUpdateListener.onFail();
//                    }
//                }
//            }
//        };
//        mThread.start();
//    }
//
//    private Handler handler = new Handler(){
//        public void handleMessage(Message msg){
//            int progress=msg.what;
//            String totalSize=(String)msg.obj;
//            refreshProgress(progress,Integer.valueOf(totalSize));
//        }
//    };
//
//    /**
//     * 刷新下载进度
//     * @param progress
//     * @param totalSize
//     * */
//    private void refreshProgress(int progress,int totalSize) {
//        int newProgress=progress;
//        if(newProgress>lastProgress){
//            lastProgress=newProgress;
//            notifyMsg("版本更新", "文件正在下载："+newProgress+"%", newProgress);
//            //加载进度接口回调
//            if(mAPPUpdateListener!=null){
//                mAPPUpdateListener.onProgress(progress,totalSize);
//            }
//        }
//        if(progress==100){
//            //下载成功接口回调
//            if(mAPPUpdateListener!=null){
//                mAPPUpdateListener.onSuccess();
//            }
//            mNotificationManager.cancel(0); //关闭下载进度通知栏
//            InstallAPK();
//            stopSelf();
//        }
//    }
//    /*** 下面部分代码是使用HttpURLConnection方法下载，不依赖okhttp（可以代替downloadFile()，功能一样） end ***/


}
