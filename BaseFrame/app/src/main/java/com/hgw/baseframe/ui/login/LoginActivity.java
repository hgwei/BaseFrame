package com.hgw.baseframe.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.bean.LoginEntry;
import com.hgw.baseframe.bean.login.LoginBean;
import com.hgw.baseframe.constants.RequestData;
import com.hgw.baseframe.constants.ResultCode;
import com.hgw.baseframe.constants.UrlConstants;
import com.hgw.baseframe.core.http.RequestPost;
import com.hgw.baseframe.core.prefs.PreferenceHelper;
import com.hgw.baseframe.ui.main.MainActivity;
import com.hgw.baseframe.util.JSONUtil;
import com.hgw.baseframe.util.NetWorkUtils;
import com.hgw.baseframe.view.CommonDialog;
import com.hgw.baseframe.view.EditTextPasswordCheck;
import java.util.HashMap;

/**
 * 描述：登录
 * @author hgw
 * */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    /***账号、密码输入框*/
    private EditText maccount;
    private EditTextPasswordCheck mpassword;
    /**密码是否可见图标*/
    private ImageView mpasswordSeeImage;
    /**密码是否可见（默认不可见）*/
    private boolean isShowPassword=false;
    /**记住密码*/
    private ImageView misRememberIcon;
    /**是否勾选了记住密码*/
    private boolean isRemember=true;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.network).setOnClickListener(this);
        findViewById(R.id.forgetPassword).setOnClickListener(this);
        maccount = findViewById(R.id.account);
        mpassword = findViewById(R.id.password);
        mpasswordSeeImage = findViewById(R.id.passwordSeeImage);
        mpasswordSeeImage.setOnClickListener(this);
        findViewById(R.id.isRememberLL).setOnClickListener(this);
        misRememberIcon = findViewById(R.id.isRememberIcon);

        //记录历史登录账号和密码
        String account =  PreferenceHelper.getInstance().getLoginAccount();
        String password =  PreferenceHelper.getInstance().getLoginPassword();
        if (!TextUtils.isEmpty(account)) {
            maccount.setText(account);
            maccount.setSelection(account.length());//将光标追踪到内容的最后
        }
        if (!TextUtils.isEmpty(password)) {
            mpassword.setText(password);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login:
                /**登录*/
                final String account = maccount.getText().toString().trim();
                final String password = mpassword.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    showShortToast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showShortToast("请输入密码");
                    return;
                }

                //检查是否有网络
                if(!NetWorkUtils.isNetworkConnected(this)){
                    showNotNetWordTipsDialog();
                    return;
                }
                //登录
                toLogin(account,password);
                break;
            case R.id.passwordSeeImage:
                /**密码是否可见*/
                if(!isShowPassword){
                    isShowPassword=true;
                    mpasswordSeeImage.setImageResource(R.mipmap.check_the_password);
                    mpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码可见
                }else{
                    isShowPassword=false;
                    mpasswordSeeImage.setImageResource(R.mipmap.close_the_password);
                    mpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT );//设置密码不可见
                }
                break;
            case R.id.isRememberLL:
                /**记住密码*/
                if(isRemember){
                    isRemember=false;
                    misRememberIcon.setImageResource(R.mipmap.login_unchecked);
                }else{
                    isRemember=true;
                    misRememberIcon.setImageResource(R.mipmap.login_select);
                }
                break;
            case R.id.network:
                /**网络设置*/
                //SetNetWorkActivity.toActivity(this);
                break;
            case R.id.forgetPassword:
                /**忘记密码*/
                ForgetPasswordActivity.toActivity(this);
                break;
            default:
                break;
        }
    }

    /**请求登录
     * @param account
     * @param password
     * */
    private void toLogin(String account, String password) {
        showProgressDialog(null);
        HashMap<String, String> params= RequestData.getLogin(account,password);
        new RequestPost(this, UrlConstants.URL_USERLOGIN,params)
                .setRequestCallback(new RequestPost.RequestCallback() {
                    @Override
                    public void onSuccess(String response) {
                        hideProgressDialog();
                        LoginBean mResult = JSONUtil.parseToJavaBean(response, LoginBean.class);
                        if (mResult.getErrorCode()== ResultCode.RESULT_OK){
                            //保存用户信息
                            saveUserInfo(mResult.getData(),account,password);
                            //跳转首页
                            MainActivity.toActivity(LoginActivity.this);
                            LoginActivity.this.finish();
                        }else{
                            showShortToast(mResult.getErrorMsg());
                        }
                    }
                    @Override
                    public void onError(String response) {
                        hideProgressDialog();
                    }
                });
    }

    /**保存用户信息*/
    private void saveUserInfo(LoginBean.Data data, String account, String password) {
        //保存账号
        PreferenceHelper.getInstance().setLoginAccount(account);
        //保存密码（勾选了才保存）
        if(isRemember){
            PreferenceHelper.getInstance().setLoginPassword(password);
        }else{
            PreferenceHelper.getInstance().setLoginPassword("");
        }
        //登录状态为已登录
        PreferenceHelper.getInstance().setLoginStatus(true);

        //把用户信息存到单例里面
        LoginEntry.Instance().setLogin(true);
        LoginEntry.Instance().setToken(data.getToken());
        LoginEntry.Instance().setUser(data);
    }

    /**没有网络提示Dialog*/
    public void showNotNetWordTipsDialog() {
        CommonDialog tipsDialog = new CommonDialog(this);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        tipsDialog.setContent("当前没有可用网络，请检查网络连接");

        tipsDialog.setPositiveButton("确定", new CommonDialog.CommonDialogInterface() {
            @Override
            public boolean onClick() {
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

    /**提示（单选）Dialog*/
    public void showTipsDialog(String str) {
        CommonDialog tipsDialog = new CommonDialog(this);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        tipsDialog.setContent(str);
        tipsDialog.setUniqueButton("我知道了", new CommonDialog.CommonDialogInterface() {
            @Override
            public boolean onClick() {
                return true;
            }
        });
        tipsDialog.show();
    }

}
