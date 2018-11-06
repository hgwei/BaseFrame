package com.hgw.baseframe.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hgw.baseframe.R;


/**
 * 描述：单个确定按钮、多个确定按钮 公用dialog
 * @author hgw
 **/
public class CommonDialog extends Dialog {

    private Context mContext;

    private LinearLayout mRootView;
    //对话框内容
    private TextView mTvContent;
    private LinearLayout mLlMultiBottom;
    //多选确认
    private TextView mTvSure;
    //多选取消
    private TextView mTvCancel;
    //单选确认
    private TextView mTvConfirm;

    //确定
    private CommonDialogInterface mInterfacePositive;

    //取消
    private CommonDialogInterface mInterfaceNegative;

    //单选确定
    private CommonDialogInterface mInterfaceUnique;

    /** 点击事件接口 */
    public interface CommonDialogInterface {
        boolean onClick();
    }


    public CommonDialog(Context context) { this(context, R.style.common_dialog); }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_common_dialog);

        //动态设置宽度
        Window window = getWindow();
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        window.setLayout(width-150, LinearLayout.LayoutParams.WRAP_CONTENT);

        mRootView = (LinearLayout) findViewById(R.id.rootView);
        mTvContent = (TextView) findViewById(R.id.tvContent);
        mLlMultiBottom = (LinearLayout) findViewById(R.id.llMultiBottom);
        mTvSure = (TextView) findViewById(R.id.tvSure);
        mTvCancel = (TextView) findViewById(R.id.tvCancel);
        mTvConfirm = (TextView) findViewById(R.id.tvConfirm);
    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent(String content) {
        mTvContent.setText(content);
    }

    /**
     * 设置确定按钮
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text, CommonDialogInterface listener) {
        mInterfacePositive = listener;
        mLlMultiBottom.setVisibility(View.VISIBLE);
        mTvSure.setText(text);
        mTvSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mInterfacePositive) {
                    mInterfacePositive.onClick();
                }
                dismiss();
            }
        });
    }

    /**
     * 设置取消按钮
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text, CommonDialogInterface listener) {
        mInterfaceNegative = listener;
        mTvCancel.setText(text);
        mTvCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mInterfaceNegative) {
                    mInterfaceNegative.onClick();
                }
                dismiss();
            }
        });
    }

    /**
     * 设置单选按钮
     *
     * @param text
     * @param listener
     */
    public void setUniqueButton(String text, CommonDialogInterface listener) {
        mInterfaceUnique = listener;
        mTvConfirm.setVisibility(View.VISIBLE);
        mTvConfirm.setText(text);
        mTvConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mInterfaceUnique) {
                    mInterfaceUnique.onClick();
                }
                dismiss();
            }
        });
    }
}
