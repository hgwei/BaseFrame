package com.hgw.baseframe.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import com.hgw.baseframe.R;

/**
 * 描述：自定义加载进度
 * @author hgw
 */

public class LoadingProgressDialog extends Dialog {
    private static Context mContext;
    private static LoadingProgressDialog dialog;


    public LoadingProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    /**显示progressDialog*/
    public static void showProgressDialog(Context context, String message){
        if(dialog!=null && dialog.isShowing()){
            return;
        }

        dialog = new LoadingProgressDialog(context, R.style.LoadingProgressDialog);//dialog样式
        dialog.setContentView(R.layout.layout_load_progress_dialog);//dialog布局文件
        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
        dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }
        });

        //提示文本，默认为（加载中...）
        TextView mMessage=(TextView) dialog.findViewById(R.id.textView);
        mMessage.setText(message);

        dialog.show();
    }

    /**关闭progressDialog*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void hideProgressDialog() {
        if(dialog!=null && dialog.isShowing()){
            Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();
            if(context instanceof Activity) {
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                    dialog.dismiss();
            }else{
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //dialog获得焦点时
        if(hasFocus && dialog != null){
        }
    }

}
