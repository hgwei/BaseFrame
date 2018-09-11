package com.hgw.baseframe.core.glide;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hgw.baseframe.R;
import java.io.File;

/**
 * 描述：Glide
 * @author hgw
 */
public class GlideHelper {
    private String TAG = "GlideHelper";
    private static GlideHelper mGlideHelper=null;

    public static GlideHelper getInstance(){
        if (mGlideHelper == null) {
            mGlideHelper = new GlideHelper();
        }
        return mGlideHelper;
    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     */
    public void loadImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.aio_image_default)
                    .error(R.mipmap.aio_image_fail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * 加载网络图片
     *
     * @param activity
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void loadImage(Activity activity, String url, ImageView imageView) {
        if (!activity.isDestroyed()) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.aio_image_default)
                    .error(R.mipmap.aio_image_fail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(activity)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    /**
     * 加载网络图片
     *
     * @param fragment
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     */
    public void loadImage(Fragment fragment, String url, ImageView imageView) {
        if (fragment != null && fragment.getActivity() != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.aio_image_default)
                    .error(R.mipmap.aio_image_fail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(fragment)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    /**
     * 加载网络图片
     *
     * @param fragment
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     */
    public void loadImage(android.app.Fragment fragment, String url, ImageView imageView) {
        if (fragment != null && fragment.getActivity() != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.aio_image_default)
                    .error(R.mipmap.aio_image_fail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(fragment)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,android.app.Fragment is null");
        }
    }

    /** 加载本地图片 */
    public void loadLocalImage(Context context, String localPath, ImageView imageView) {
        if(context!=null){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.aio_image_default)
                    .error(R.mipmap.aio_image_fail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(Uri.fromFile(new File(localPath)))
                    .apply(options)
                    .into(imageView);
        }else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }
}
