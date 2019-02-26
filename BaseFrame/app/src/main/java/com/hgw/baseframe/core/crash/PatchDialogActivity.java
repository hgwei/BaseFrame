package com.hgw.baseframe.core.crash;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.hgw.baseframe.R;

/**
 * 描述：APP异常提示Dialog
 * @author hgw
 * */
public class PatchDialogActivity extends Activity {

    public static void toActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PatchDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);

        ultimateSolution();
    }


    private void ultimateSolution() {
        String title = "BaseFrame";
        String ultimateMessage = "对不起！应用程序出现异常退出了。请点击 重启 按钮重启当前应用进行重试。";
        new AlertDialog.Builder(this, R.style.CrashAlertDialogStyle)
            .setTitle(title)
            .setMessage(ultimateMessage)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher)
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override public void onCancel(DialogInterface dialog) {
                    finish();
                }
            })
            .setNegativeButton("退出", new DialogInterface.OnClickListener() {

                @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            })
            .setPositiveButton("重启", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    restart();
                }
            }).show();
    }


    private void restart() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
