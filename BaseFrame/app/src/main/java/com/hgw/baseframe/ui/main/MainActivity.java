package com.hgw.baseframe.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseFragmentActivity;
import com.hgw.baseframe.constants.Constants;

/**
 * 描述：MainActivity
 * @author hgw
 * */
public class MainActivity extends BaseFragmentActivity {
    /**定义FragmentManager对象*/
    private FragmentManager fManager;
    /**首页 Fragment*/
    private HomeFragment mHomeFragment;
    /**项目 Fragment*/
    private ProjectFragment mProjectFragment;
    /**消息 Fragment*/
    private MessageFragment mMessageFragment;
    /**我的 Fragment*/
    private PersonFragment mPersonFragment;

    private ImageView miv1,miv2,miv3,miv4;
    private TextView mtv1,mtv2,mtv3,mtv4;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        registerBoradcastReceiver();
        tab1_click(null);
    }

    private void initView() {
        fManager = getSupportFragmentManager();

        miv1= findViewById(R.id.iv1);
        miv2= findViewById(R.id.iv2);
        miv3= findViewById(R.id.iv3);
        miv4= findViewById(R.id.iv4);
        mtv1= findViewById(R.id.tv1);
        mtv2= findViewById(R.id.tv2);
        mtv3= findViewById(R.id.tv3);
        mtv4= findViewById(R.id.tv4);
    }

    /**首页*/
    public void tab1_click(View v) {
        miv1.setImageResource(R.mipmap.icon_main_tab1_sel);
        miv2.setImageResource(R.mipmap.icon_main_tab2_nor);
        miv3.setImageResource(R.mipmap.icon_main_tab3_nor);
        miv4.setImageResource(R.mipmap.icon_main_tab4_nor);
        mtv1.setTextColor(0xff18C8A1);
        mtv2.setTextColor(0xff7E8894);
        mtv3.setTextColor(0xff7E8894);
        mtv4.setTextColor(0xff7E8894);

        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            transaction.add(R.id.content, mHomeFragment);
        } else {
            transaction.show(mHomeFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**项目*/
    public void tab2_click(View v) {
        miv1.setImageResource(R.mipmap.icon_main_tab1_nor);
        miv2.setImageResource(R.mipmap.icon_main_tab2_sel);
        miv3.setImageResource(R.mipmap.icon_main_tab3_nor);
        miv4.setImageResource(R.mipmap.icon_main_tab4_nor);
        mtv1.setTextColor(0xff7E8894);
        mtv2.setTextColor(0xff18C8A1);
        mtv3.setTextColor(0xff7E8894);
        mtv4.setTextColor(0xff7E8894);

        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        if (mProjectFragment == null) {
            mProjectFragment = new ProjectFragment();
            transaction.add(R.id.content, mProjectFragment);
        } else {
            transaction.show(mProjectFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**消息*/
    public void tab3_click(View v) {
        miv1.setImageResource(R.mipmap.icon_main_tab1_nor);
        miv2.setImageResource(R.mipmap.icon_main_tab2_nor);
        miv3.setImageResource(R.mipmap.icon_main_tab3_sel);
        miv4.setImageResource(R.mipmap.icon_main_tab4_nor);
        mtv1.setTextColor(0xff7E8894);
        mtv2.setTextColor(0xff7E8894);
        mtv3.setTextColor(0xff18C8A1);
        mtv4.setTextColor(0xff7E8894);

        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
            transaction.add(R.id.content, mMessageFragment);
        } else {
            transaction.show(mMessageFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**我的*/
    public void tab4_click(View v) {
        miv1.setImageResource(R.mipmap.icon_main_tab1_nor);
        miv2.setImageResource(R.mipmap.icon_main_tab2_nor);
        miv3.setImageResource(R.mipmap.icon_main_tab3_nor);
        miv4.setImageResource(R.mipmap.icon_main_tab4_sel);
        mtv1.setTextColor(0xff7E8894);
        mtv2.setTextColor(0xff7E8894);
        mtv3.setTextColor(0xff7E8894);
        mtv4.setTextColor(0xff18C8A1);

        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        if (mPersonFragment == null) {
            mPersonFragment = new PersonFragment();
            transaction.add(R.id.content, mPersonFragment);
        } else {
            transaction.show(mPersonFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**隐藏所有的Fragment,避免fragment混乱*/
    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mProjectFragment != null) {
            transaction.hide(mProjectFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
        if (mPersonFragment != null) {
            transaction.hide(mPersonFragment);
        }
    }

    /**重写onSaveInstanceState方法，而注释掉super是为了解决home键回到应用，页面显示出错问题*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    /**发送角色切换广播*/
//    public void sendBroadcast(String messageNum){
//        Intent intentBroadCast = new Intent();
//        intentBroadCast.setAction(Constants.BROADCAST_MESSAGE_NUM_CHANGE);
//        intentBroadCast.putExtra("messageNum",messageNum);
//        sendBroadcast(intentBroadCast);
//    }

    /**
     * 注册广播
     */
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.BROADCAST_MESSAGE_NUM_CHANGE);
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    /**
     * 接收广播
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.BROADCAST_MESSAGE_NUM_CHANGE)) {
                /**未读已读消息数改变广播*/
                if(intent!=null){
                    String messageNum=intent.getExtras().getString("messageNum");
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mBroadcastReceiver);
    }

}
