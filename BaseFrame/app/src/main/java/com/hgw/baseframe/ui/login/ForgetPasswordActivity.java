package com.hgw.baseframe.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.view.EditTextPasswordCheck;

/**
 * 描述：忘记密码
 * @author hgw
 * */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText mmobile,mcode;
    private String mobile,code;
    private TextView mgetCode;
    /**初始化倒计时的数据*/
    private int second=60;

    private EditText mpassword1,mpassword2;
    private String password1,password2;
    /**密码是否可见图标*/
    private ImageView mpasswordSeeImage1,mpasswordSeeImage2;
    /**密码是否可见（默认不可见）*/
    private boolean isShowPassword1=false,isShowPassword2=false;


    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler!=null)
            handler.removeCallbacks(runnable);
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.sure).setOnClickListener(this);
        mmobile = findViewById(R.id.mobile);
        mcode = findViewById(R.id.code);
        mgetCode = findViewById(R.id.getCode);
        mgetCode.setOnClickListener(this);

        mpassword1 = findViewById(R.id.password1);
        mpassword2 = findViewById(R.id.password2);
        mpasswordSeeImage1 = findViewById(R.id.passwordSeeImage1);
        mpasswordSeeImage1.setOnClickListener(this);
        mpasswordSeeImage2 = findViewById(R.id.passwordSeeImage2);
        mpasswordSeeImage2.setOnClickListener(this);
    }

    /**短信验证码60秒定时器*/
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            second--;
            mgetCode.setText(second+"s");
            //判断倒计时有没有结束
            if(second<=0){
                handler.removeCallbacks(runnable);
                mgetCode.setText("获取验证码");
                second=60;
            }else{
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                /**返回*/
                this.finish();
                break;
            case R.id.passwordSeeImage1:
                /**新密码是否可见*/
                if(!isShowPassword1){
                    isShowPassword1=true;
                    mpasswordSeeImage1.setImageResource(R.mipmap.ic_password_see);
                    mpassword1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码可见
                }else{
                    isShowPassword1=false;
                    mpasswordSeeImage1.setImageResource(R.mipmap.ic_password_nosee);
                    mpassword1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT );//设置密码不可见
                }
                break;
            case R.id.passwordSeeImage2:
                /**再次输入新密码是否可见*/
                if(!isShowPassword2){
                    isShowPassword2=true;
                    mpasswordSeeImage2.setImageResource(R.mipmap.ic_password_see);
                    mpassword2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码可见
                }else{
                    isShowPassword2=false;
                    mpasswordSeeImage2.setImageResource(R.mipmap.ic_password_nosee);
                    mpassword2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT );//设置密码不可见
                }
                break;
            case R.id.getCode:
                /**获取验证码*/
                mobile=mmobile.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    showShortToast("请先输入用户号/手机号");
                    return;
                }
                if(TextUtils.equals(mgetCode.getText().toString().trim(), "获取验证码")){
                    /**使用handler去启动一个新线程来倒计时*/
                    handler.postDelayed(runnable, 0);
                    //请求获取验证码
                    getCode();
                }
                break;
            case R.id.sure:
                /**确定*/
                mobile=mmobile.getText().toString().trim();
                code=mcode.getText().toString().trim();
                password1=mpassword1.getText().toString().trim();
                password2=mpassword2.getText().toString().trim();

                if(TextUtils.isEmpty(mobile)){
                    showShortToast("请先输入用户号/手机号");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showShortToast("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    showShortToast("请输入新密码");
                    return;
                }
                if(TextUtils.isEmpty(password2)){
                    showShortToast("请再次输入新密码");
                    return;
                }
                if(!TextUtils.equals(password1,password2)){
                    showShortToast("两次输入的新密码不一致，请重新输入");
                    return;
                }
                postSetPassword();
                break;
            default:
                break;
        }
    }

    /**获取验证码*/
    private void getCode() {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //模拟验证码输入
                mcode.setText("1234");

                //获取验证码按钮恢复
                handler.removeCallbacks(runnable);
                mgetCode.setText("获取验证码");
                second=60;
            }
        }, 5000);
    }

    /**修改密码*/
    private void postSetPassword() {
        showShortToast("修改密码成功");
        this.finish();
    }

}
